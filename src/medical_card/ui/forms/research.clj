(ns medical-card.ui.forms.research
  (:require [medical-card.ui.create-event-dialog :refer [create-record-selector-form]]
            [medical-card.schemas.core-schemas :refer [Research]]
            [medical-card.schemas.utils :refer [schema-entry->form-params get-top-level-entries]]
            [medical-card.schemas.form-schemas :refer [ResearchFormSchema EventFormSchema DocumentFormSchema]]))


(def ^:const input-types
  {:string "text"})

(def form-to-schema
  {:research ResearchFormSchema
   :event EventFormSchema
   :document DocumentFormSchema})

(comment
  (= Research (:research form-to-schema))
  :rcf)


(defn inputs-from-schema
  [input-cols]
  (into
   [:div]
   (for [{:keys [name type default-value display-name]} input-cols]
     [:div {:class "input-group"}
      [:div {:class "col-md-12"}
       [:label {:for name :class "form-label"}
        display-name]]
      [:div {:class "col-md-12"}
       [:input
        {:id name
         :name name
         :type (get input-types type)
         :value default-value
         :class "form-control"}]]])))


(defn research-form
  ([] (research-form "research"))
  ([val]
   (let [schemas-col (as-> val $
                       (keyword $)
                       (get form-to-schema $)
                       (get-top-level-entries $)
                       (map schema-entry->form-params $))]
     (-> schemas-col
         inputs-from-schema
         (create-record-selector-form :value val)))))


(comment
  (inputs-from-schema [{:name "testname" :type :string :display-name "Поле!"}])
  (research-form)
  :rcf)
