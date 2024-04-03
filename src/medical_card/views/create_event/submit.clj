(ns medical-card.views.create-event.submit
  (:require [clojure.pprint :refer [pprint]]
            [clojure.walk :refer [keywordize-keys]]
            [malli.core :as m]
            [medical-card.schemas.form-schemas :refer [ResearchFormSchema]]
            [medical-card.schemas.utils :refer [strict-json-transformer]]))


(defn create-event
  [form-params]
  (pprint (as-> form-params $
            (keywordize-keys $)
            (m/coerce ResearchFormSchema $ strict-json-transformer))))

(comment
  (def a {;"select-object" "research",
          "name" "qewrwqqewrwerqwer",
          "description" "rwqer",
          "type" "unscheduled_health_check",
          "start_date" ""})

  (def b
    {;:select-object "research",
     :name "qewrqwerwqer",
     :description "qwerqwerqwer",
     :type "routine_health_check",
     :start_date "2024-04-01T20:41:11.877930"
     })

  :rcf)