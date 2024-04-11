(ns medical-card.views.ajax
  (:require [medical-card.db :refer [db]]
            [medical-card.db.actions :refer [enrich-choices!]]
            [medical-card.ui.forms.research :refer [research-form
                                                    schema-entry->forms-params]]
            [ring.util.response :as r]
            [rum.core :as rum]))


(defn get-create-event-view []
  (->> (partial enrich-choices! db)
       (schema-entry->forms-params "research")
       (research-form "research")
       rum/render-html
       r/response))



(defn get-record-form-view
  [object-for]
  (->> (partial enrich-choices! db)
       (schema-entry->forms-params object-for)
       (research-form object-for)
       (rum/render-html)
       (r/response)))


(comment
  (->> (partial enrich-choices! db)
       (schema-entry->forms-params "event")
       )
  :rcf)

