(ns medical-card.schemas.event
  (:require [malli.core :as m]
            [malli.generator :as mg]
            [cheshire.core :refer [generate-string parse-string]]
            [malli.transform :as mt]
            [malli.experimental.time :as met]
            [malli.registry :as mr]
            [malli.experimental.time.generator]
            [java-time :as t]
            [malli.json-schema :as json-schema]
            [malli.experimental.time.transform :as mett]))


;; подключение экспериментальных схем дат
(mr/set-default-registry!
 (mr/composite-registry
  (m/default-schemas)
  (met/schemas)))


(def User [])

;; routine_health_check - плановая проверка
;; unscheduled_health_check - внеплановая проверка
;; disease - недуг
;; other
(def Research
  [:map
   [:name [string? {:min 10 :max 200}]]
   [:description [string? {:min 0 :max 2000}]]
   [:start_date [:maybe inst?]]
   [:type
    [:enum
     "routine_health_check"
     "unscheduled_health_check"
     "disease"
     "other"]]
   [:start_date [:maybe inst?]]])


(def EventType [])


;; doctor_visit - прием врача
;; taking_tests - сдача анализов
(def Event
  [:map
   [:type
    [:enum
     "doctor_visit"
     "taking_tests"]]
   [:name [string? {:min 5 :max 200}]]
   [:description [string? {:min 0 :max 2000}]]
   [:event_type_id {:optional true} [:maybe int?]]
   [:parent_id {:optional true} [:maybe int?]]
   [:research_id {:optional true} [:maybe int?]]
   [:updated_at {:optional true} [:maybe inst?]]])


(def EventType [])


(def Document
  [:map
   [:name [string? {:min 5 :max 200}]]
   [:path [string? {:min 5 :max 200 :optional true}]]
   [:type [string? {:min 5 :max 200 :optional true}]]
   [:document [string? {:optional true}]]
   [:user_id [int?]]
   [:event_id [int? {:optional true}]]
   [:doctor_id [int? {:optional true}]]
   [:report_date [:maybe inst?]]])


(def Doctor [])


(def Specialization [])


(def Organization [])


(def DoctorToSpecialization [])


(def DoctorToOrganization [])


(comment
  (m/decode [:time/local-date {:pattern "yyyyMMdd"}] "20200101" (mett/time-transformer))
  (m/decoder [:time/local-date] (mett/time-transformer))

  (m/decode
   [:string {:decode {:string clojure.string/upper-case}}]
   "kerran" mt/string-transformer)

  (m/coerce (m/schema Event) {:type "doctor_visit" :name "qwre12341234qer" :description ""}
            mt/json-transformer)

  (m/coerce (m/schema Research) {:name "qwerqwer1234123412340w"
                                 :description ""
                                 :start_date "2022-03-08"
                                 :finish_date "2022-03-08"
                                 :type "disease"}
            mt/json-transformer)


  (m/schema? (m/schema Research))
  (t/format (:start_date (mg/generate (m/schema Research))))

  :rcf)
