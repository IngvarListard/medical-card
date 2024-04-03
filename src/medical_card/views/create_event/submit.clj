(ns medical-card.views.create-event.submit
  (:require [clojure.pprint :refer [pprint]]
            [clojure.walk :refer [keywordize-keys]]))


(defn create-event
  [form-params]
  (pprint (as-> form-params $
            (keywordize-keys $)))
  ;; (pprint (as-> form-params $
  ;;           (keywordize-keys $)
  ;;           ((fn [params] (println params) params) $)
  ;;           (m/coerce Research $ submit-form-transformer)))

  ;; (println (m/coerce Research (keywordize-keys form-params) mt/json-transformer))
  )

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