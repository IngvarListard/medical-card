#_{:clj-kondo/ignore [:unresolved-var]}
(ns medical-card.schemas.core-schemas
  "Дополнительные ключи
 - :display-name -- человекочитаемое название для отображения на клиенте
 - :dbtable -- соответствующая схеме таблица в БД
 - :choices -- поле для человекочитаемого описания значений enum"
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

            [malli.util :as mu]))


;; подключение экспериментальных схем для обработки дат
(mr/set-default-registry!
 (mr/composite-registry
  (m/default-schemas)
  (met/schemas)))

(def research-choices
  {"routine_health_check" "Плановая проверка"
   "unscheduled_health_check" "Внеплановая проверка"
   "disease" "Недуг"
   "other" "Другое"})


(def Research
  [:map {:display-name "Исследование" :dbtable researches}
   [:name {:display-name "Название"} [:string {:min 3 :max 200}]]
   [:description {:display-name "Описание"} [:string {:min 0 :max 2000}]]
   [:type
    {:display-name "Тип исследования" :choices research-choices}
    (into [:enum] (keys research-choices))]
   [:start_date {:optional true :display-name "Дата начала"}
    [:maybe [inst?]]]])


(def event-choices
  {"doctor_visit" "Визит к врачу"
   "taking_tests" "Сдача анализов"})


(def Event
  [:map {:display-name "Событие" :dbtable events}
   [:id {:optional true} [:maybe int?]]
   [:name {:display-name "Название события"} [string? {:min 5 :max 200}]]
   [:description {:display-name "Описание/Доп. информация"} [string? {:min 0 :max 2000}]]
   [:type
    {:display-name "Тип события" :choices event-choices}
    (into [:enum] (keys event-choices))]
   [:event_type_id {:optional true :display-name "Тип события"} [:maybe int?]]
   [:parent_id
    {:optional true
     :display-name "Предшествующее событие"
     :db {:ref {:for Event :to :id}}}
    [:maybe int?]]
   [:research_id {:optional true :display-name "Исследование"} [:maybe int?]]
   [:updated_at {:optional true} [:maybe inst?]]])


(m/form Event)
(def document-choices
  {"doctor_opinion" "Заключение врача"
   "tests_result" "Результаты анализов"
   "symptoms" "Симптомы"})


(def Document
  [:map {:display-name "Документ" :dbtable documents}
   [:name {:display-name "Название документа"} [:string {:min 5 :max 200}]]
   [:path {:display-name "Путь к файлу"} [:string {:min 5 :max 200 :optional true}]]
   [:type {:display-name "Тип документа" :choices document-choices}
    (into [:enum] (keys document-choices))]
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
