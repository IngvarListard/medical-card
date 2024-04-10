(ns medical-card.ui.forms.research
  (:require [medical-card.schemas.form-schemas :refer [DocumentFormSchema
                                                       EventFormSchema
                                                       ResearchFormSchema]]
            [medical-card.schemas.utils :refer [get-top-level-entries
                                                schema-entry->form-params]]
            [medical-card.ui.forms.create-event-dialog :refer [create-record-selector-form]]
            [medical-card.ui.components.inputs :refer [form-params->input]]))


(def form->schema
  {:research ResearchFormSchema
   :event EventFormSchema
   :document DocumentFormSchema})


(defn schema-entry->forms-params
  ([] (schema-entry->forms-params "research" nil))
  ([form] (schema-entry->forms-params form nil))
  ([form enrich-choices]
   (let [enrich-choices* (if enrich-choices
                           (fn [[_ e _ :as entry]]
                             (if-let [ech (:enrich-choices e)]
                               (enrich-choices (:hsql ech) (:transform ech))
                               entry))
                           identity)]
     (as-> form $
       (keyword $)
       (get form->schema $)
       (get-top-level-entries $)
       (map enrich-choices* $)
       (map schema-entry->form-params $)))))


(defn research-form
  ([] (research-form "research" nil))
  ([form] (research-form form nil))
  ([form schemas-col]
   (let [schemas-col* (or schemas-col (schema-entry->forms-params form))]
     (as-> schemas-col* $
       (map form-params->input $)
       (create-record-selector-form $ :value form)))))


(comment
  (research-form)

  (let [schemas-col (as-> "research" $
                      (keyword $)
                      (get form->schema $)
                      (get-top-level-entries $)
                      (map schema-entry->form-params $))]
    (as-> schemas-col $
      (map form-params->input $)
      (create-record-selector-form $ :value val)))
  :rcf)
