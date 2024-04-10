(ns medical-card.ui.components.inputs)


(def inputs-hierarchy (-> (make-hierarchy)
                          (derive :string :text)
                          (derive :int :text)
                          (derive 'string? :text)
                          (derive 'inst? :date)
                          (derive 'int? :text)
                          (derive :enum :select)
                          atom))

(defmulti form-params->input (fn [v & _] (:type v)) :hierarchy inputs-hierarchy)


(defmethod form-params->input :select
  select-input
  [schema-params & opts]
  (let [{:keys [name default-value display-name choices]} schema-params]
    [:label {:class "form-control"}
     [:div {:class "label"}
      [:span {:class "label-text"} display-name]]
     [:div
      (nth opts 0)
      (into
       [:select.select.select-bordered.w-full.max-w-md.select-sm
        (merge
         {:id name
          :name name
          :aria-label display-name}
         (when default-value
           {:value default-value}))]
       (map (fn [[k v]] [:option {:value k} v]) choices))]]))

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
  [schema-params & _]
  (let [{:keys [name default-value display-name]} schema-params]
    [:label.form-control
     [:div.label
      [:span.label-text display-name]]
     [:input.input.input-bordered.w-full.max-w-md.input-sm
      {:id name
       :name name
       :type "text"
       :value default-value}]]))

(defmethod form-params->input :date
  date-input
  [schema-params & _]
  (let [{:keys [name display-name]} schema-params]
    [:label.form-control
     [:div.label
      [:span.label-text display-name]]
     [:input.input.input-bordered.w-full.max-w-md.input-sm
      {:id    name
       :name  name
       :type  "date"}]]))


(comment
  (form-params->input {:type :enum})
  (form-params->input {:type :text})
  (form-params->input {:type :int})

  :rcf)
