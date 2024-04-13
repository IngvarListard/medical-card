(ns medical-card.schemas.form-schemas
  (:require [honey.sql.helpers :refer [from select] :as h]
            [malli.util :as mu]
            [medical-card.schemas.core-schemas :refer [Document Event Research]]
            [medical-card.schemas.utils :refer [table]]))

(def ResearchFormSchema
  Research)

(defn update-entry-prop
  [schema entry props]
  (mu/merge schema [:map [entry props (mu/get schema entry)]]))

(def EventFormSchema
  (-> Event
      (mu/dissoc :event_type_id)
      (mu/assoc :parent_id [:maybe :string])
      (update-entry-prop
       :parent_id
       {:enrich-choices {:hsql (-> (select :id :name)
                                   (from (table Event)))
                         :transform (fn [query-result]
                                      {(str (:events/id query-result)) (:events/name query-result)})}})
      (mu/assoc :research_id [:maybe :string])
      (update-entry-prop
       :research_id
       {:enrich-choices {:hsql (-> (select :id :name)
                                   (from (table Research)))
                         :transform (fn [query-result]
                                      {(str (:researches/id query-result)) (:researches/name query-result)})}})
      (mu/dissoc :updated_at)))



(def DocumentFormSchema
  (-> Document
      (mu/assoc :user_id [:maybe :string])
      (mu/assoc :event_id [:maybe :string])
      (update-entry-prop
       :event_id
       {:enrich-choices {:hsql (-> (select :id :name)
                                   (from (table Event)))
                         :transform (fn [query-result]
                                      {(str (:events/id query-result)) (:events/name query-result)})}})
      (mu/assoc :doctor_id [:maybe :string])
      ;; (mu/assoc :report_date [:maybe :string])
      (mu/dissoc :report_date)
      (mu/dissoc :user_id)
      (mu/dissoc :path)))

(comment
  :rcf)