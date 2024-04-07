(ns medical-card.schemas.form-schemas
  (:require [malli.util :as mu]
            [medical-card.schemas.core-schemas :refer [Document Event Research]]
            [medical-card.db.tables :refer [researches events documents]]))

(def ResearchFormSchema
  Research)

(def EventFormSchema
  (-> Event
      (mu/dissoc :event_type_id)
      (mu/assoc :parent_id [:maybe :string])
      (mu/assoc :research_id [:maybe :string])
      (mu/dissoc :updated_at)))

(def DocumentFormSchema
  (-> Document
      (mu/assoc :user_id [:maybe :string])
      (mu/assoc :event_id [:maybe :string])
      (mu/assoc :doctor_id [:maybe :string])
      ;; (mu/assoc :report_date [:maybe :string])
      (mu/dissoc :report_date)
      (mu/dissoc :user_id)
      (mu/dissoc :path)))

(comment
  :rcf)