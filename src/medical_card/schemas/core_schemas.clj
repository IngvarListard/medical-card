#_{:clj-kondo/ignore [:unresolved-var]}
(ns medical-card.schemas.core-schemas
  (:require [malli.core :as m]
            [malli.registry :as mr]
            [malli.experimental.time :as met]
            [malli.experimental.time.generator]
            [medical-card.db.tables :refer [researches events documents]]
            ;; [malli.transform :as mt]
            ;; [malli.util :as mu]
            ;; [malli.generator :as mg]
            ;; [cheshire.core :refer [generate-string parse-string]]
            ;; [java-time :as t]
            ;; [malli.json-schema :as json-schema]
            ;; [malli.experimental.time.transform :as mett]
            ))


;; подключение экспериментальных схем для обработки дат
(mr/set-default-registry!
 (mr/composite-registry
  (m/default-schemas)
  (met/schemas)))

(def research-types
  {"routine_health_check" "Плановая проверка"
   "unscheduled_health_check" "Внеплановая проверка"
   "disease" "Недуг"
   "other" "Другое"})

(defn named-enum
  [enum]
  (as-> enum $
    (conj [:enum] $)
    (concat $ (keys enum))
    (into [] $)))

(def Research
  "Исследование
  - routine_health_check - плановая проверка
  - unscheduled_health_check - внеплановая проверка
  - disease - недуг
  - other
 "
  [:map {:display-name "Исследование" :dbtable researches}
   [:name {:display-name "Имя"} [:string {:min 3 :max 200}]]
   [:description {:display-name "Описание"} [:string {:min 0 :max 2000}]]
   [:type
    {:display-name "Тип исследования"}
    (named-enum research-types)
    [:enum (keys research-types) research-types]]
   [:start_date {:optional true :display-name "Дата начала"}
    [:maybe [inst?]]]])

(comment
  (m/properties (named-enum research-types))
  (concat [:enum] ["a" "b" "c"])
  (m/form Research)
  :rcf)


(def Event
  "Событие
    doctor_visit - прием врача
    taking_tests - сдача анализов"
  [:map {:display-name "Событие" :dbtable events}
   [:name {:display-name "Название события"} [string? {:min 5 :max 200}]]
   [:description {:display-name "Описание"} [string? {:min 0 :max 2000}]]
   [:type
    {:display-name "Тип события"}
    [:enum
     "doctor_visit"
     "taking_tests"]]
   [:event_type_id {:optional true :display-name "Тип события"} [:maybe int?]]
   [:parent_id {:optional true :display-name "Предшествующее событие"} [:maybe int?]]
   [:research_id {:optional true :display-name "Исследование"} [:maybe int?]]
   [:updated_at {:optional true} [:maybe inst?]]])


(def Document
  [:map {:display-name "Документ" :dbtable documents}
   [:name {:display-name "Название документа"} [:string {:min 5 :max 200}]]
   [:path {:display-name "Путь к файлу"} [:string {:min 5 :max 200 :optional true}]]
   [:type {:display-name "Тип"} [:string {:min 5 :max 200}]]
   [:document {:display-name "Документ" :optional true} [:maybe :string]]
   [:user_id {:display-name "Пользователь" :optional true} [:maybe :int]]
   [:event_id {:display-name "Событие" :optional true} [:maybe :int]]
   [:doctor_id {:display-name "Врач" :optional true} [:maybe :int]]
   [:report_date [:maybe inst?]]])


;;later
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
