(ns medical-card.schemas.core-schemas-test
  (:require
   [malli.core :as m]
   [clojure.test :refer [deftest is testing]]
   [medical-card.schemas.core-schemas :as subj]
   [medical-card.test.utils :refer [not-thrown?]]))

(deftest get-top-level-entries-test
  (testing "Test select only one field"
    (is (let [name-schema (subj/get-top-level-entries subj/Research [:name])
              [[field-name & _]] name-schema]
          (and
           (= (count name-schema) 1)
           (= field-name :name)))))
  (testing "Test select multiple fields"
    (is (let [multi-schema (subj/get-top-level-entries subj/Research [:name :description :type :start_date])]
          (= (count multi-schema) 4)))))


(deftest Research-test
  (testing "Test Reseach valid malli schema"
    (is (not-thrown? (partial m/schema subj/Research)))))


(deftest schema-entry->form-params-test
  (testing "Test transform simple schema"
    (is (let [schema [:map [:desc {:display-name "Имя"} [:string]]]
              [e] (subj/get-top-level-entries schema)]
          (= (subj/schema-entry->form-params e)
             {:name "desc"
              :type :string
              :display-name "Имя"
              :allowed-values nil}))))
  (testing "Test transform complex schema"
    (is (let [schema [:map [:choices {:display-name "Имя"} [:enum "foo" "bar" "baz"]]]
              [e] (subj/get-top-level-entries schema)]
          (= (subj/schema-entry->form-params e)
             {:name "choices"
              :type :enum
              :display-name "Имя"
              :allowed-values ["foo" "bar" "baz"]})))))