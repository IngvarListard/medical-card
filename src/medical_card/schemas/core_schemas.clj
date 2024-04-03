(ns medical-card.schemas.core-schemas
  (:require [malli.core :as m]
            [malli.util :as mu]
            [malli.registry :as mr]
            [malli.experimental.time :as met]
            [malli.experimental.time.generator]
            [malli.transform :as mt]
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


(def Research
  "Исследование
  - routine_health_check - плановая проверка
  - unscheduled_health_check - внеплановая проверка
  - disease - недуг
  - other
 "
  [:map {:display-name "Исследование"}
   [:name {:display-name "Имя"} [:string {:min 10 :max 200}]]
   [:description {:display-name "Описание"} [:string {:min 0 :max 2000}]]
   [:type
    {:display-name "Тип исследования"}
    [:enum
     "routine_health_check"
     "unscheduled_health_check"
     "disease"
     "other"]]
   [:start_date {:optional true :display-name "Дата начала"}
    [:maybe [inst?]]]])


(def Event
  "Событие
    doctor_visit - прием врача
    taking_tests - сдача анализов"
  [:map
   [:type
    {:display-name "Тип исследования"}
    [:enum
     "doctor_visit"
     "taking_tests"]]
   [:name {:display-name "Название события"} [string? {:min 5 :max 200}]]
   [:description {:display-name "Описание"} [string? {:min 0 :max 2000}]]
   [:event_type_id {:optional true :display-name "Тип события"} [:maybe int?]]
   [:parent_id {:optional true :display-name "Предшествующее событие"} [:maybe int?]]
   [:research_id {:optional true :display-name "Исследование"} [:maybe int?]]
   [:updated_at {:optional true} [:maybe inst?]]])


(def Document
  [:map
   [:name {:display-name "Название документа"} [:string {:min 5 :max 200}]]
   [:path {:display-name "Путь к файлу"} [:string {:min 5 :max 200 :optional true}]]
   [:type {:display-name "Тип"} [:string {:min 5 :max 200}]]
   [:document {:display-name "Документ" :optional true} [:string]]
   [:user_id {:display-name "Пользователь" :optional true} [:int]]
   [:event_id {:display-name "Событие" :optional true} [:int]]
   [:doctor_id {:display-name "Врач" :optional true} [:int]]
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


;; рабочий вариант здесь
(comment


  :rcf)
