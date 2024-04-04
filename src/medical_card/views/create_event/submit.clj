(ns medical-card.views.create-event.submit
  (:require [clojure.walk :refer [keywordize-keys]]
            [malli.core :as m]
            [medical-card.db :refer [db]]
            [medical-card.db.actions :as asql]
            [medical-card.schemas.form-schemas :refer [ResearchFormSchema]]
            [medical-card.schemas.utils :refer [strict-json-transformer]]
            [medical-card.ui.forms.research :refer [form-to-schema
                                                    research-form]]
            [next.jdbc :as jdbc]
            [ring.util.response :as r]
            [rum.core :as rum]))


(defn create-event!
  [form-params]
  (let [form-kw (as-> form-params $
                  (get $ "select-object")
                  (keyword $))
        schema (form-kw form-to-schema)]
    (as-> form-params $
      (keywordize-keys $)
      (m/coerce schema $ strict-json-transformer)
      (asql/create schema [$])
      (jdbc/execute! db $))
    (-> form-kw
        research-form
        rum/render-html
        r/response)))


(comment
  ;; (keys (ns-publics 'next.jdbc))

  (def a {;"select-object" "research",
          "name" "qewrwqqewrwerqwer",
          "description" "rwqer",
          "type" "unscheduled_health_check",
          "start_date" ""})

  (def b
    {;:select-object "research",
     :name "qewrqwerwqer",
     :description "qwerqwerqwer",
     :type "routine_health_check",
     :start_date "2024-04-01T20:41:11.877930"})

  (def a
    {:select-object "research",
     :name "qwerwe13243241234",
     :description "qwerqwerqwerqwe",
     :type "routine_health_check",
     :start_date nil})

  (def a
    {:select-object "research",
     :name "qwerwe13243241234",
     :description "qwerqwerqwerqwe",
     :type "routine_health_check",
     :start_date nil})

  (m/coerce ResearchFormSchema a strict-json-transformer)

  :rcf)