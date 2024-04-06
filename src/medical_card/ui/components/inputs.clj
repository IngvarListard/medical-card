(ns medical-card.ui.components.inputs)


(def inputs-hierarchy (-> (make-hierarchy)
                          (derive :string :text)
                          (derive :int :text)
                          (derive 'string? :text)
                          (derive 'inst? :text)
                          (derive :enum :select)
                          atom))

(defmulti form-params->input #(:type %) :hierarchy inputs-hierarchy)


(defmethod form-params->input :select
  select-input
  [schema-params]
  (let [{:keys [name default-value display-name choices]} schema-params]
    [:div
     (into
      [:select
       (merge
        {:id name
         :name name
         :class "form-select"
         :aria-label display-name}
        (when default-value
          {:value default-value}))]
      (map (fn [[k v]] [:option {:value k} v]) choices))]))

(comment
  (form-params->input
   {:type :enum :name "foo"
    :default-value "default"
    :display-name "Фу"
    :choices {"bar" "Бар"
              "baz" "Баз"}})
  (map (fn [i] i) {:a "a" :b "b"})
  :rcf)


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
