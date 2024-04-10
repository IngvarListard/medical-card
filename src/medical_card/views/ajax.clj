(ns medical-card.views.ajax
  (:require [medical-card.db :refer [db]]
            [medical-card.db.actions :refer [enrich-choices!]]
            [medical-card.ui.forms.research :refer [research-form
                                                    schema-entry->forms-params]]
            [ring.util.response :as r]
            [rum.core :as rum]))


(defn get-create-event-view []
  (as-> (schema-entry->forms-params "research" enrich-choices!) $
    (research-form "research" $)
    rum/render-html
    r/response))


(comment
  (as-> (schema-entry->forms-params "research" (partial enrich-choices! db)) $
    (research-form "research" $))
  :rcf)


(defn get-record-form-view
  [object-for]
  (as-> object-for $
    (schema-entry->forms-params $ enrich-choices!)
    (research-form object-for $)
    rum/render-html
    r/response))

