(ns medical-card.db.queries
  (:require [medical-card.db :refer [db]]
            [next.jdbc :as jdbc]
            [honey.sql :as sql]))


(def asdf {:select [:*]
           :from [:documents]
           :left-join [:doctors [:= :documents.doctor_id :doctors.id]
                       :events [:= :documents.event_id :events.id]]})


(defn get-documents []
  (jdbc/execute! db (sql/format asdf)))
