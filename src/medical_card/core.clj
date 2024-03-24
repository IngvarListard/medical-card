(ns medical-card.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [compojure.route :as route]
            [compojure.core :as c]
            [ring.util.response :refer [response]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-response]]
            [rum.core :as r]
            [next.jdbc :as jdbc]
            [medical-card.views.index :refer [medical-history-view test-form-view]]
            [medical-card.ui.core :refer [page]])
  (:gen-class))


(c/defroutes app-routes
  (c/GET "/" [] (response (r/render-html (medical-history-view))))
  (c/POST "/store" [] (fn [& args] (println 13241) (response "wqerwqer")))
  (c/GET "/test" [] (r/render-html (page (test-form-view))))
  ;; (c/POST "/clicked" [] (response (r/render-html (dialog))))
  (route/resources "/")
  (route/not-found "Not Found"))


(defn -main
  [& _]
  (run-jetty app-routes {:port 3000
                         :join? false}))

(comment
  (run-jetty app-routes {:port 3000
                         :join? false})

  (def server (run-jetty
               (-> #'app-routes
                   wrap-reload
                   wrap-json-response)
               {:port 3000 :join? false}))


  (require '[medical-card.db :refer [db]])
  (require '[next.jdbc :as jdbc])
  (def db2
    {:dbtype "sqlite"
     :dbname     "resources/database/db.sqlite3"
     :classname   "org.sqlite.JDBC"})
  (jdbc/execute! db2 ["select * from organizations"])
  (def ccc (jdbc/execute! db2 ["select * from organizations"]))
  (r/render-html (medical-history-view))
  :rcf)
