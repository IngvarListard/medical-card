(ns medical-card.db.helpers
  (:require [honey.sql :as hsql]
            [honey.sql.helpers :refer [join]]))


(defn transform-table-fields
  "Преобразование параметра table-fields в мапу table и fields отдельно"
  [table-fields]
  (cond
    (vector? table-fields) {:table  (first table-fields)
                            :fields (subvec table-fields 1)}
    (keyword? table-fields) {:table  table-fields
                             :fields [:*]}))

(defn build-where-cond [condition]
  (let [where-cond (map #(into [:=] %1) condition)
        where (if (<= (count condition) 1)
                where-cond
                (into [:and] where-cond))]
    where))

(defn get-by
  "Общая фунция для получения таблиц из БД"
  ([table-fields condition & {:keys [order-by offset limit join]}]
   (let [t&f (transform-table-fields table-fields)
         where (build-where-cond condition)
         sql-map (merge {:select (:fields t&f)
                         :from   (:table t&f)
                         :where  where}
                        (when order-by {:order-by order-by})
                        (when offset {:offset offset})
                        (when limit {:limit limit})
                        (when join {:join order-by}))]
     sql-map)))

(comment
  (get-by [:documents :*] [])
  :rcf)

(defn build-where-condition
  [condition]
  (let [where-cond (map #(into [:=] %1) condition)
        where (if (<= (count condition) 1)
                where-cond
                (into [:and] where-cond))]
    where))

(defn update-by
  [table-fields condition object]
  (println "condition" condition)
  (let [multi? (if (vector? object) true false)
        t&f (transform-table-fields table-fields)
        where (build-where-condition condition)
        sql-map {:update    (:table t&f)
                 :set       object
                 :where     where
                 :returning (:fields t&f)}]
    sql-map))

(comment
  (as-> :document $
    (join $ :directors [:= :films.director_id :directors.id])
    (hsql/format $)))

(defn insert-into
  "Общая функция для вставки записи в таблицу БД"
  [table-fields object]
  (let [multi? (if (vector? object) true false)
        t&f (transform-table-fields table-fields)
        object (cond
                 (map? object) [object]
                 (vector? object) object)
        sql-map {:insert-into (:table t&f)
                 :columns     (keys (first object))
                 :values      (map #(vals %1) object)
                 :returning   (:fields t&f)}]
    sql-map))