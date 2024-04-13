(ns medical-card.core
  (:require [rum.core :as rum]
            [compojure.core :as c]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.util.response :as r]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.json :refer [wrap-json-response]]
            [medical-card.views.index-page :refer [medical-history-view]]
            [medical-card.views.ajax :as ajax-views]
            [medical-card.views.create-event.submit :refer [create-event!]]
            [ring.util.codec :refer [form-decode]]
            [clojure.walk :refer [keywordize-keys]]
            ;; [medical-card.ui.components.calendar :refer [calendar]]
            [medical-card.ui.test-alpine :refer [test-alpine]]
            [medical-card.ui.core :refer [page]]
            [hiccup2.core :as h])
  (:gen-class))


(c/defroutes app-routes
  (c/GET "/" [] (r/response (rum/render-html (medical-history-view))))
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
  ;; (c/GET "/calendar" [] (r/response (rum/render-static-markup (page (calendar)))))
  (c/GET "/test-alpine" [] (r/response (rum/render-static-markup (page (test-alpine)))))
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
