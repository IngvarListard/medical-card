(ns medical-card.schemas.utils
  (:require [malli.core :as m]
            [malli.experimental.time.generator]
            [malli.transform :as mt]
            [malli.util :as mu]))


;; Работает
(def json-transformer
  "Кастомный json-transformer. Обработка пустых строк как nil для дат"
  (mt/transformer
   (let [json-transformer* (m/-transformer-chain (mt/json-transformer))]
     {:decoders (merge (:decoders json-transformer*)
                       {'inst? (fn [v] (when (seq v) mt/-string->date))})
      :encoders (:encoders json-transformer*)})))

(def strict-json-transformer
  (mt/transformer
   mt/strip-extra-keys-transformer
   json-transformer))


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

