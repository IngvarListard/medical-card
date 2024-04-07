(ns medical-card.ui.components.inputs)


(def inputs-hierarchy (-> (make-hierarchy)
                          (derive :string :text)
                          (derive :int :text)
                          (derive 'string? :text)
                          (derive 'inst? :date)
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
       [:select
        (merge
         {:id name
          :name name
          :aria-label display-name
          :class "select select-bordered w-full max-w-md"}
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
    [:label {:class "form-control"}
     [:div {:class "label"}
      [:span {:class "label-text"} display-name]]
     [:input
      {:id name
       :name name
       :type "text"
       :value default-value
       :class "input input-bordered w-full max-w-md"}]]))

(defmethod form-params->input :date
  date-input
  [schema-params & _]
  (let [{:keys [name default-value display-name]} schema-params]
    [:div
     [:div {:class "input-group"}
      [:div {:class "col-md-12"}
       [:label {:for name :class "form-label"}
        display-name]]]
     [:div
      {:class "row justify-content-center"}
      [:div
       {:class "col-lg-12 col-sm-12"}
       [:input {:id name :name name :class "form-control" :type "date"}]
       [:span {:id "startDateSelected"}]]]]))


(comment
  (form-params->input {:type :enum})
  (form-params->input {:type :text})
  (form-params->input {:type :int})

  :rcf)
