(ns medical-card.test.utils)


(defn not-thrown?
  [f]
  (try (f) true
       (catch Exception _ false)))