(ns medical-card.schema-test.schema-core-test
  (:require [clojure.test :refer :all]
            [medical-card.core :refer :all]
            [medical-card.schemas.event :refer [Research]]
            [malli.core :as m]
            [medical-card.utils :refer [not-thrown?]]))


(deftest all-schemas-valid-test
  (testing "Research schema"
    (is (not-thrown? (partial m/schema Research)))))