(ns medical-card.core
  (:require [rum.core :as rum]
            [compojure.core :as c]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.util.response :as r]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-response]]
            [medical-card.views.index-page :refer [medical-history-view]]
            [medical-card.views.ajax :as ajax-views]
            [ring.util.codec :refer [form-decode]]
            [clojure.walk :refer [keywordize-keys]])
  (:gen-class))


(c/defroutes app-routes
  (c/GET "/" [] (r/response (rum/render-html (medical-history-view))))
  (c/GET "/api/forms/create-event" [] (ajax-views/get-create-event-view))
  (c/GET "/api/forms/create-event/object-form" {form :query-string} (fn [& _] (-> form
                                                                               form-decode
                                                                               keywordize-keys
                                                                               :select-object
                                                                               ajax-views/get-record-form-view)))  ;; TODO: заменить research на аргумент запроса
  (c/POST "/api/create-event" [] (fn [& all] (println all) (r/response "wqerwqer")))
  ;; (c/POST "/clicked" [] (response (r/render-html (dialog))))
  (route/resources "/")
  (route/not-found "Not Found"))


(defn -main
  [& _]
  (run-jetty app-routes {:port 3000
                         :join? false}))

(defonce server (run-jetty
                 (-> #'app-routes
                     wrap-reload
                     wrap-json-response)
                 {:port 3000 :join? false}))

(comment
  (def server (run-jetty
               (-> #'app-routes
                   wrap-reload
                   wrap-json-response)
               {:port 3000 :join? false}))

  (rum/render-html (medical-history-view))
  :rcf)
