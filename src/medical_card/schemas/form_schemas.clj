(ns medical-card.schemas.form-schemas
  (:require [malli.core :as m]
            [malli.util :as mu]
            [medical-card.schemas.core-schemas :refer [Research Event Document]]
            [clojure.instant :as instant]))

(def ResearchFormSchema
  (mu/merge
   Research
   [:map
    [:start_date {:optional true :display-name "Дата начала"}
     [:multi {:dispatch :type :decode/before '#(update % :type keyword)}
      [:maybe [inst?]]
      [::m/default [:maybe :string]]]]]))

(def EventFormSchema
  (-> Event
      (mu/assoc :event_type_id [:maybe :string])
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