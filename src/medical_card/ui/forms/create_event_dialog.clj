(ns medical-card.ui.forms.create-event-dialog
  (:require [medical-card.constants :refer [object-type-choices]]
            [rum.core :as r]
            [medical-card.ui.components.inputs :refer [form-params->input]]))


(def ^:const ^:private ctx
  {::form-id "new-event-form"
   ::selector-id "select-object"})


(defn t-id
  "Target id"
  [f-id]
  (->> ctx f-id (str "#")))


(defn- new-event-dialog-btn []
  [:button
   {:type "button",
    :class "btn btn-primary",
    :onclick "my_modal_1.showModal()"
    :hx-get "/api/forms/create-event"
    :hx-target (t-id ::form-id)
    :hx-swap "outerHTML"}
   "Добавить"])


(r/defc create-record-selector-form
  "Форма с селектором выбора типа объекта для создания"
  ([] (create-record-selector-form ""))
  ([form-content & {:keys [_] :as v}]
   [:form {:hx-post "/api/forms/create-event" 
           :id (::form-id ctx)}
    (form-params->input {:choices object-type-choices
                         :name (::selector-id ctx)
                         :default-value (:value v)
                         :display-name "Создаваемый объект"
                         :type :select}
                        {:hx-trigger "change"
                         :hx-get "/api/forms/create-event/object-form"
                         :hx-target (t-id ::form-id)
                         :hx-swap "outerHTML"
                         :hx-include (format "[name='%s']" (::selector-id ctx))})
    form-content
    [:div {:class " modal-footer "}
     [:button
      {:type " button ",
       :class " btn btn-secondary ",
       :data-bs-dismiss " modal "}
      " Close "]
     [:button {:type " submit ", :class " btn btn-primary "}
      " Save changes "]]]))


(defn dialog
  []
  [:dialog.modal#my_modal_1
   [:div.modal-box
    [:form {:id (::form-id ctx)}
     [:button
      {:type " button ",
       :class " btn-close ",
       :data-bs-dismiss " modal ",
       :aria-label " Close "}]]]])


(defn new-event-dialog []
  [:div
   ;; Button trigger modal
   (new-event-dialog-btn)
   ;; Modal
   (dialog)])