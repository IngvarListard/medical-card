(ns medical-card.ui.components.inputs)


(def inputs-hierarchy (-> (make-hierarchy)
                          (derive :string :text)
                          (derive :int :text)
                          (derive :enum :select)
                          atom))

(defmulti form-params->input #(:type %) :hierarchy inputs-hierarchy)



(defmethod form-params->input :select
  select-input
  [schema-params]
;;   (let [{:keys [name type default-value display-name]} schema-params])
  1)


(defmethod form-params->input :text
  text-input
  [schema-params]
  (let [{:keys [name default-value display-name]} schema-params]
    (into
     [:div
      [:div {:class "input-group"}
        [:div {:class "col-md-12"}
         [:label {:for name :class "form-label"}
          display-name]]
        [:div {:class "col-md-12"}
         [:input
          {:id name
           :name name
           :type "text"
           :value default-value
           :class "form-control"}]]]])))


(comment
  (form-params->input {:type :enum})
  (form-params->input {:type :text})
  (form-params->input {:type :int})

  :rcf)
