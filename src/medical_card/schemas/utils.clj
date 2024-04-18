(ns medical-card.schemas.utils
  (:require [malli.core :as m]
            [malli.experimental.time.generator]
            [malli.transform :as mt]
            [malli.util :as mu]))


(def web-form-transformer
  "Трансформер для веб формы -- преобразование пустых строк в nil"
  (mt/transformer
   {:name :string->nil
    :decoders
    {:int mt/-string->long
     'int? mt/-string->long
     :string mt/-string->nil
     'inst? mt/-string->nil}}))

(comment
  (mt/-string->long "sdf")
  (mt/-string->nil "adsf")

  (m/coerce [:map [:t [:int]]] {:t "666999"} web-form-transformer)

  :rcf)


(def strict-web-form-transformer
  "Отбрасывает из схемы несуществующие поля"
  (mt/transformer
   web-form-transformer
   mt/strip-extra-keys-transformer
   mt/json-transformer))


(defn get-top-level-entries
  "Разбить map схему на список входящих в неё полей"
  ([schema] (get-top-level-entries schema nil))
  ([schema fields]
   (let [schema* (if fields (mu/select-keys schema fields) schema)]
     (map (fn [[k s]]
            [k (m/properties s) (m/form (mu/get schema k))])
          (m/entries schema*)))))


(defn schema-entry->form-params
  "Преобразование схемы malli в контекст для создания формы"
  ([entry]
   (let [[entry-name opts schema] entry
         choices (:choices opts)
         typ (or
              (:input-type opts)
              (loop [s schema
                     t (m/type schema)]  ;; get end-type of linear schema
                (cond
                  (not (some #{t} [:maybe])) t
                  :else (recur
                         (mu/get s 0) (m/type (mu/get s 0))))))]
     {:name (name entry-name)
      :type typ
      :display-name (:display-name opts)
      :choices choices})))


(defn schema-ref->choices
  [schema-entry]
  (let [[entry opts schema] schema-entry]
    ;; (if-let [ref (:ref opts)]
    ;;     )
    schema))

(comment
  (schema-entry->form-params [:name {:test "val"} :string])
  (schema-ref->choices [:name {:test "val"} :string])
  :rcf)

(defn table
  [schema]
  (->  schema m/properties :dbtable))

(comment
  ;; TODO: move to test
  ;; test 2
  (schema-entry->form-params [:description {:display-name "Описание"} [:maybe [:string {:min 0, :max 2000}]]])
  (mu/get [:maybe [:string {:min 0, :max 2000}]] 0)
  ;;test 1
  (m/properties [:enum {:choices {"routine_health_check" "Плановая проверка", "unscheduled_health_check" "Внеплановая проверка", "disease" "Недуг", "other" "Другое"}} "routine_health_check" "unscheduled_health_check" "disease" "other"])
  (schema-entry->form-params [:type
                              {:display-name "Тип исследования"}
                              [:enum
                               {:choices
                                {"routine_health_check" "Плановая проверка",
                                 "unscheduled_health_check" "Внеплановая проверка",
                                 "disease" "Недуг",
                                 "other" "Другое"}}
                               "routine_health_check"
                               "unscheduled_health_check"
                               "disease"
                               "other"]])

  (require '[medical-card.schemas.core-schemas :refer [Research]])
  (get-top-level-entries Research)
  (mu/subschemas [:maybe :string])
  (mu/get [:maybe {} :string] 0)

  (loop [s [:maybe :string]]
    (let [typ (m/type s)]
      (cond
        (not (some #{typ} [:maybe])) typ
        :else (recur (mu/get s 0)))))

  (some #{:maybe} [:maybe])
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

