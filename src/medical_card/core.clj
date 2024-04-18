(ns medical-card.core
  (:require [clojure.walk :refer [keywordize-keys]]
            [compojure.core :as c]
            [compojure.route :as route]
            [medical-card.ui.core :refer [page]]
            [medical-card.ui.test-alpine :refer [calendar]]
            [medical-card.views.ajax :as ajax-views]
            [medical-card.views.create-event.submit :refer [create-event!]]
            [medical-card.views.index-page :refer [medical-history-view]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.util.codec :refer [form-decode]]
            [ring.util.response :as r]
            [rum.core :as rum])
  (:gen-class))


(c/defroutes app-routes
  (c/GET "/" [] (r/response (rum/render-static-markup (medical-history-view))))
  (c/GET "/api/forms/create-event" [] (ajax-views/get-create-event-view))
  (c/POST "/api/forms/create-event" [] (fn [{:keys [form-params] :as _request}]
                                         (create-event! form-params)))
  (c/GET "/api/forms/create-event/object-form"
    {form-params :query-string}
    (fn [& _] (-> form-params
                  form-decode
                  keywordize-keys
                  :select-object
                  ajax-views/get-record-form-view)))
  (c/GET "/calendar" [] (r/response (calendar)))
  (route/resources "/")
  (route/not-found "Not Found"))


(defn -main
  [& _]
  (run-jetty app-routes {:port 3000
                         :join? false}))


#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defonce server
  (run-jetty
   (-> #'app-routes
       wrap-reload
       wrap-json-response
       wrap-params)
   {:port 3000 :join? false}))


(comment
  (.stop server)
  (.start server)
  (require 'kaocha.watch
           'kaocha.repl)
  (kaocha.watch/run (kaocha.repl/config))
  (def server
    (run-jetty
     (-> #'app-routes
         wrap-reload
         wrap-json-response
         wrap-params)
     {:port 3000 :join? false}))

  (rum/render-html (medical-history-view))
  :rcf)
