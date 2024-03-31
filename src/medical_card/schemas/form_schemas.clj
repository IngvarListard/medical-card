(ns medical-card.schemas.form-schemas
  (:require [malli.core :as m]
            [malli.util :as mu]
            [medical-card.schemas.core-schemas :refer [Research get-top-level-entries entry-schema->form-params]]))

(def ResearchFormSchema
  (mu/merge
   Research
   [:map
    [:start_date {:optional true :display-name "Дата начала"} [:maybe :string]]]))

(comment
  :rcf)