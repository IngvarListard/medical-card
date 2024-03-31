(ns medical-card.ui.create-event-dialog
  (:require [medical-card.constants :refer [object-types]]
            [rum.core :as r]
            [medical-card.ui.forms.list-const :as c]))


(def ^:const ^:private ctx
  {:form-id "new-event-form"
   :selector-id "select-object-input"})


(defn t-id
  "Target id"
  [f-id]
  (->> ctx f-id (str "#")))


(defn- new-event-dialog-btn []
  [:button
   {:type "button",
    :class "btn btn-primary",
    :data-bs-toggle "modal",
    :data-bs-target "#new-event"
    :hx-get "/api/forms/create-event"
    :hx-target (t-id :form-id)
    :hx-swap "outerHTML"}
   "Добавить запись"])


(defn selector
  "Селектор типов объектов для создания"
  [items & {:keys [value] :as v}]
  [:div
   {:hx-trigger "change"
    :hx-get "/api/forms/create-event/object-form"
    :hx-target (t-id :form-id)
    :hx-swap "outerHTML"
    :hx-include "[name='select-object']"}
   (into
    [:select
     (merge
      {:id (:selector-id ctx)
      :name (:select-object c/forms)
      :class "form-select"
      :aria-label "Выберите тип записи"}
      (when value v))]
    (map (fn [i] [:option {:value (:name i)} (:display-name i)]) items))])


(r/defc create-record-selector-form
  "Форма с селектором выбора типа объекта для создания"
  ([] (create-record-selector-form ""))
  ([form-content & {:keys [_] :as v}]
   [:form {:hx-post "/api/forms/create-event" :id (:form-id ctx)}
    (selector object-types v)
    form-content
    [:div {:class "modal-footer"}
     [:button
      {:type "button",
       :class "btn btn-secondary",
       :data-bs-dismiss "modal"}
      "Close"]
     [:button {:type "submit", :class "btn btn-primary"}
      "Save changes"]]]))


(defn dialog
  []
  [:div
   {:class "modal fade",
    :id "new-event",
    :tabindex "-1",
    :aria-labelledby "new-event-label",
    :aria-hidden "true"}
   [:div {:class "modal-dialog"}
    [:div {:class "modal-content"}
     [:div {:class "modal-header"}
      [:h1 {:class "modal-title fs-5", :id "new-event-label"}
       "Новая запись"]]
     [:div {:class "modal-body"}
      [:form {:id (:form-id ctx)}
       [:button
        {:type "button",
         :class "btn-close",
         :data-bs-dismiss "modal",
         :aria-label "Close"}]]]]]])


(defn new-event-dialog []
  [:div
   ;; Button trigger modal
   (new-event-dialog-btn)
   ;; Modal
   (dialog)])