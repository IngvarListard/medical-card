(ns medical-card.test.schema-test.schema-core-test
  (:require [clojure.test :refer [deftest testing is]]
            [medical-card.schemas.core-schemas :refer [Research]]
            [malli.core :as m]
            [medical-card.test.utils :refer [not-thrown?]]))


(deftest all-schemas-valid-test
  (testing "Research schema"
    (is (not-thrown? (partial m/schema Research)))))