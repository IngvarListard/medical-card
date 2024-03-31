(ns medical-card.schemas.core-schemas
  (:require [clojure.string :refer [blank?]]
            [malli.core :as m]
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


;; подключение экспериментальных схем дат
(mr/set-default-registry!
 (mr/composite-registry
  (m/default-schemas)
  (met/schemas)))


(def Research
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
    [:multi {:dispatch :type}
     [:maybe [inst?]]
     [::m/default [:maybe :string]]]]])

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


(defn get-top-level-entries
  ([schema] (get-top-level-entries schema nil))
  ([schema fields]
   (let [schema* (if fields (mu/select-keys schema fields) schema)]
     (map (fn [[k s]]
            [k (m/properties s) (m/form (mu/get schema k))])
          (m/entries schema*)))))


(defn schema-entry->form-params
  [entry]
  (let [[entry-name opts schema] entry
        ch (m/children schema)]
    {:name (name entry-name)
     :type (cond (coll? schema) (first schema) :else schema)
     :display-name (:display-name opts)
     :allowed-values ch}))


(comment
  (type (nth (get-top-level-entries Research) 0))

  (schema-entry->form-params (nth (get-top-level-entries Research) 0))


  (map schema-entry->form-params (vec (get-top-level-entries Research)))
  (schema-entry->form-params [:name {:display-name "Имя"} [:string {:min 10, :max 200}]])
  (m/entries Research)
  (get-top-level-entries Research [:name])
  (m/properties Research {::m/walk-entry-vals true})
  (mu/get Research :name)
  (m/walk
   Research
   (fn [schema path children options]
     (println schema path children options))
   {::m/walk-entry-vals true})

  :rcf)


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


(defn nullify-str []
  (mt/transformer
   {:decoders
    {:string
     {:compile (fn [_ _]
                 (fn [x]
                   (if (and (string? x) (blank? x)) nil x)))}}}))


(def submit-form-transformer
  (mt/transformer
   nullify-str
   mt/json-transformer))


(comment
  (def a [:map
          [:d {:optional true :display-name "date"} [:multi {:dispatch :type}
                                                     [:maybe inst?]
                                                     [::m/default [:maybe :string]]]]])
  (mu/update-properties [:map
                         [:name {:name "qwerqwer"} [:int]]]
                        assoc :name 1)

  (mu/merge
   [:map
    [:name {:optional true :name "shit"} [int?]]]
   [:map
    [:name {:name "foo"} [int?]]])
  (m/walk a (fn [schema path children options]
              (println schema path children options)
              1))
  (m/coerce a {:d ""}
            submit-form-transformer)
  (m/properties [:string {:min 10, :max 200, :display-name "Имя"}])
  (key {:a 1})
  (require '[clojure.pprint :refer [pprint]])
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

