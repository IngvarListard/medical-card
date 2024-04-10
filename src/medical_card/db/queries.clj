(ns medical-card.db.queries
  (:require [medical-card.db :refer [db]]
            [next.jdbc :as jdbc]
            [honey.sql :as sql]
            [honey.sql.helpers :refer [from select] :as h]))


(def asdf {:select [:*]
           :from [:documents]
           :left-join [:doctors [:= :documents.doctor_id :doctors.id]
                       :events [:= :documents.event_id :events.id]]})


(defn get-documents []
  (jdbc/execute! db (sql/format asdf)))

(comment
  (:events/id (nth (jdbc/execute! db (sql/format (-> (select :id :name)
                                    (from :events)))) 0))

  :rcf)
