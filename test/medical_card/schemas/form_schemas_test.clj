(ns medical-card.schemas.form-schemas-test
  (:require
   [malli.core :as m]
   [clojure.test :refer [deftest is testing]]
   [medical-card.schemas.form-schemas :as subj]
   [medical-card.test.utils :refer [not-thrown?]]))

(deftest ResearchFormSchema-test
  (testing "Test ReseachFormSchema valid malli schema"
    (is (not-thrown? (partial m/schema subj/ResearchFormSchema)))))

(deftest EventFormSchema-test
  (testing "Test EventFormSchema valid malli schema"
    (is (not-thrown? (partial m/schema subj/EventFormSchema)))))

(deftest DocumentFormSchema-test
  (testing "Test DocumentFormSchema valid malli schema"
    (is (not-thrown? (partial m/schema subj/DocumentFormSchema)))))