(ns medical-card.utils)


(defn not-thrown?
  [f]
  (try (f) true
       (catch Exception _ false)))