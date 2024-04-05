(ns medical-card.schemas.utils
  (:require [malli.core :as m]
            [malli.experimental.time.generator]
            [malli.transform :as mt]
            [malli.util :as mu]))


;; Работает
(def json-transformer
  "Грязь для преобразования пустых строк в nil"
  (mt/transformer
   (let [json-transformer* (m/-transformer-chain (mt/json-transformer))]
     {:decoders (merge (:decoders json-transformer*)
                       {'inst? (fn [v] (when (seq v) mt/-string->date))})
      :encoders (:encoders json-transformer*)})))

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
  (mt/transformer
   web-form-transformer
   mt/strip-extra-keys-transformer
   mt/json-transformer))


(defn get-top-level-entries
  ([schema] (get-top-level-entries schema nil))
  ([schema fields]
   (let [schema* (if fields (mu/select-keys schema fields) schema)]
     (map (fn [[k s]]
            [k (m/properties s) (m/form (mu/get schema k))])
          (m/entries schema*)))))


(defn schema-entry->form-params
  "Преобразование схемы malli в контекст для создания формы"
  [entry]
  (let [[entry-name opts schema] entry
        ch (m/children schema)]
    {:name (name entry-name)
    ;; get end-type of linear schema
     :type (loop [s schema]
             (let [typ (m/type s)]
               (cond
                 (not (some #{typ} [:maybe])) typ
                 :else (recur (mu/get s 0)))))
     :display-name (:display-name opts)
     :allowed-values ch}))

(comment
  ;; TODO: move to tests
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

