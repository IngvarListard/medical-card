(ns medical-card.db.tables)

(defmacro defs
  [bindings]
  {:pre [(even? (count bindings))]}
  `(do
     ~@(for [[sym init] (partition 2 bindings)]
         `(def ~sym ~init))))

(defs
  [documents :documents
   users :users
   doctors :doctors
   organizations :organizations
   events :events])
