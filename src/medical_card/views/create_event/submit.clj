(ns medical-card.views.create-event.submit
  (:require [medical-card.schemas.core-schemas :refer [Research submit-form-transformer]]
            [malli.core :as m]
            [malli.transform :as mt]
            [clojure.walk :refer [keywordize-keys]]
            [clojure.pprint :refer [pprint]]))


(defn create-event
  [form-params]
  (pprint (as-> form-params $
            (keywordize-keys $)
            (m/coerce Research $ submit-form-transformer)))

  ;; (println (m/coerce Research (keywordize-keys form-params) mt/json-transformer))
  )

(comment
  (def a {;"select-object" "research",
          "name" "qewrwqqewrwerqwer",
          "description" "rwqer",
          "type" "unscheduled_health_check",
          "start_date" ""})


  (m/coerce [:map
             [:start_date [inst?]]] {:start_date nil} submit-form-transformer)
  (create-event a)
  (as-> a $
    (keywordize-keys $)
    (m/explain Research $ submit-form-transformer))
  :rcf)