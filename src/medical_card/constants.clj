(ns medical-card.constants)


(def object-types-map 
  {:research "research"
   :event "event"
   :document "document"})

(let [o object-types-map]
  (def object-types
  [{:name (:research o) :display-name "Исследование"}
   {:name (:event o) :display-name "Событие"}
   {:name (:document o) :display-name "Документ"}
    ;; {:name "doctor" :display-name "Врач"}
    ;; {:name "organization" :display-name "Организация"}
   ]))
