(ns medical-card.db
  (:require [next.jdbc :as jdbc]))


(def db
  {:dbtype "sqlite"
   :dbname "resources/database/db.sqlite3"
   :classname  "org.sqlite.JDBC"})