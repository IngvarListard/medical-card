(ns medical-card.schemas.event
  (:require [malli.core :as m]
            [malli.util :as mu]
            [malli.registry :as mr]
            [malli.experimental.time :as met]
            [malli.experimental.time.generator]
            ;; [malli.generator :as mg]
            ;; [cheshire.core :refer [generate-string parse-string]]
            ;; [malli.transform :as mt]
            ;; [java-time :as t]
            ;; [malli.json-schema :as json-schema]
            ;; [malli.experimental.time.transform :as mett]
            ))


;; подключение экспериментальных схем дат
(mr/set-default-registry!
 (mr/composite-registry
  (m/default-schemas)
  (met/schemas)))


(def Research
  [:map {:display-name "Исследование"}
   [:name [:string {:min 10 :max 200 :display-name "Имя"}]]
   [:description [:string {:min 0 :max 2000 :display-name "Описание"}]]
   [:type
    [:enum
     {:display-name "Тип исследования"}
     "routine_health_check"
     "unscheduled_health_check"
     "disease"
     "other"]]
   [:start_date [inst? {:display-name "Дата"}]]])

;; routine_health_check - плановая проверка
;; unscheduled_health_check - внеплановая проверка
;; disease - недуг
;; other

(def Event
  "Событие
    doctor_visit - прием врача
    taking_tests - сдача анализов"
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


(defn get-top-level-subschemas
  "Получить вложенные первого уровня из базовой схемы
   
   Example:
    (get-top-level-subschemas Research [:name])
   "
  ([schema] (get-top-level-subschemas schema nil))
  ([schema fields]
   (let [fields (mu/keys (if fields
                           (mu/select-keys schema fields)
                           schema))
         fields-schemas (map (fn [k] [k (m/form (mu/get schema k))]) fields)]
     fields-schemas)))


(defn schema-entry-to-form-params
  "Подготовка данных из Malli's Entry в удобный для работы словарь
   
   Examples:
    (schema-entry-to-form-params [:name [:string {:min 10, :max 200, :display-name \"Имя\"}]])
    (schema-entry-to-form-params (nth (get-top-level-subschemas Research [:name]) 0))
   "
  [schema-entry]
  (let [[name & [schema]] schema-entry
        props (m/properties schema)
        child (m/children schema)]
    {:name name
     :type (first schema)
     :display-name (:display-name props)
     :allowed-values child}))


(defn walk-properties
  "С помощью этой функции можно получить доступ к свойствам полей в :map"
  [schema f]
  (m/walk
   schema
   (fn [s _ c _]
     (m/into-schema
      (m/-parent s)
      (f (m/-properties s))
      (cond->> c (m/entries s) (map (fn [[k p s]] [k (f p) (first (m/children s))])))
      (m/options s)))
   {::m/walk-entry-vals true}))

;; later
(comment
  (def EventType [])
  (def EventType [])
  (def Doctor [])
  (def Specialization [])
  (def Organization [])
  (def DoctorToSpecialization [])
  (def User [])
  (def DoctorToOrganization [])
  :rcf)

(comment
  (m/properties [:string {:min 10, :max 200, :display-name "Имя"}])
  (key {:a 1})
  (get-top-level-subschemas Research)
  (mu/select-keys Research nil)
  (let [[x & _] (m/-children (mu/get Research :type))]
    _)
  (walk-properties Research (fn [p] (println p) p))
  (m/walk Research (m/schema-walker (fn [s] (println s) s)))
  (m/-properties (m/schema Research))
  (m/-properties (m/schema [:name [:string {:optional true}]]))
  (m/properties [:map [:name {:display-name "asdf"} [:string {:optional true}]]])
  (m/-properties (m/schema (last Research)))
  (m/schema? (m/schema [:name {:display-name "asdf"} [:string {:optional true}]]))
  (m/properties (mu/get (m/schema Research) :type))
  :rcf)
