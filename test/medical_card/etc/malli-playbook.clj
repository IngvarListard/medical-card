#_{:clj-kondo/ignore [:namespace-name-mismatch]}
(ns medical-card.etc.malli-playbook
  (:require [malli.core :as m]
            [malli.experimental.time.generator]
            [malli.transform :as mt]
            [malli.util :as mu]
            [medical-card.schemas.utils :refer [get-top-level-entries
                                                schema-entry->form-params
                                                json-transformer]]
            [medical-card.schemas.core-schemas :refer [Research]]))


;; пример реализации даты json inst при пустой строке
(comment
  (def tf
    (mt/transformer
     mt/json-transformer
     {:name :date-transformer}))

  (defn string->local-date
    [s]
    (when (seq s)
      (mt/-string->date s)))



  (def date-schema
    [:map
     [:date {:decode/date-transformer string->local-date
             :optional true}
      inst?]])

  (m/coerce date-schema {:date ""} tf)
  ;; => {:date nil}
  (m/decode date-schema {:date "2018-04-03"} tf)
  ;; => {:date #object[java.time.LocalDate 0x425e7c3b "2018-04-03"]}
  (m/validate date-schema (m/decode date-schema {:date "2018-04-03"} tf))
  ;; => true
  (m/validate date-schema (m/decode date-schema {:date ""} tf))
  ;; => true
  (m/validate date-schema {:date nil})
  ;; => true
  (m/validate date-schema {:date (LocalDate/now)})
  ;; => true
  (m/validate date-schema {:date ""})
  ;; => false
  )

(comment

  (def tf
    (mt/transformer
     mt/json-transformer
     {:name :date-transformer}))

  (:decoders (mt/json-transformer))
  (m/-transformer-chain (mt/json-transformer))


  (defn string->local-date
    [s]
    (when (seq s)
      (LocalDate/parse s)))

  (def date-schema
    [:map
     [:date inst?]])

  (m/decode date-schema {:date ""} json-transformer)
  ;; => {:date nil}
  (m/decode date-schema {:date "2018-04-03"} tf)
  ;; => {:date #object[java.time.LocalDate 0x425e7c3b "2018-04-03"]}
  (m/validate date-schema (m/decode date-schema {:date "2018-04-03"} tf))
  ;; => true
  (m/validate date-schema (m/decode date-schema {:date ""} tf))
  ;; => true
  (m/validate date-schema {:date nil})
  ;; => true
  (m/validate date-schema {:date (LocalDate/now)})
  ;; => true
  (m/validate date-schema {:date ""})

  (def trans
    (mt/transformer
  ;;  mt/json-transformer
     {:name :string
      :decoder {'#(not (seq %)) (identity nil)}}))
  (mt/-string-decoders)
  (m/coerce
   [:map
    [:d [:or [inst?] [:string]]]]
   {:d ""}
   trans)

  (m/explain
   [:map
    [:d inst?]]
   {:d "2024-04-01T20:41:11.877930"}
   mt/json-transformer)


  (m/decode
   [string? {:decode/string '(constantly #(str "olipa_" %))}]
   "kerran" mt/string-transformer)

  (m/decode
   [string? {:decode/string {:enter clojure.string/upper-case}}]
   "kerran" mt/string-transformer)
  (m/decode
   [inst? {:decode/inst? {:enter clojure.string/upper-case}}]
   "kerran" mt/string-transformer))


;; Malli properties
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