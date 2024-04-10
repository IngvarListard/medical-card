(ns medical-card.db.actions
  (:require [honey.sql :as sql]
            [malli.core :as m]
            [medical-card.schemas.core-schemas :refer [Research]]
            [medical-card.schemas.utils :refer [strict-web-form-transformer]]
            [next.jdbc :as jdbc]))


(defn create
  [schema data]
  (when (seq data)
    (let [cols (-> data first keys vec)
          table (-> schema m/properties :dbtable)]
      (-> {:insert-into [table]
           :columns cols
           :values (map vals data)}
          (sql/format {:pretty true})))))


(defn enrich-choices!
  [db sql-map transform]

  (->> sql-map
       (jdbc/execute! db)
       (map transform)))


(comment
  (def a
    {:select-object "research",
     :name "qwerwe13243241234",
     :description "qwerqwerqwerqwe",
     :type "routine_health_check",
     :start_date ""})
  (m/coerce Research a strict-web-form-transformer)
  (println (create Research [a]))
  (println (create Research []))
  (create Research [])


  (m/coerce [:map [:id [:maybe int?]]] {:id "1"} strict-web-form-transformer)
  :rcf)