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
  [items]
  [:div
   [:label {:for (:selector-id ctx) :class "form-label"}
    "Объект записи"]
   (into
    [:select
     {:id (:selector-id ctx)
      :name (:select-object c/forms)
      :class "form-select"
      :aria-label "Выберите тип записи"
      ;; выполняем подзагрузку формы при открытии диалога
      :hx-trigger "load-object-form from:body"
      :hx-get "/api/forms/create-event/object-form"
      ;; передаем параметр выбранный в селекторе при подгрузке формы
      :hx-include "[name='select-object']"}]
    (map (fn [i] [:option {:value (:name i)} (:display-name i)]) items))])


(r/defc formm
  ([] (formm ""))
  ([form-content]
   [:form {:hx-post "/api/create-event"}
    [:div {:class "input-group"}
     (selector object-types)
     form-content
     [:div {:class "modal-footer"}
      [:button
       {:type "button",
        :class "btn btn-secondary",
        :data-bs-dismiss "modal"}
       "Close"]
      [:button {:type "submit", :class "btn btn-primary"}
       "Save changes"]]]]))


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


;; (defn new-event-form
;;   [submit-btn]
;;   [:form {:hx-post "/api/create-event"}
;;    (selector object-types)
;;    submit-btn])


(defn new-event-dialog []
  [:div
   ;; Button trigger modal
   (new-event-dialog-btn)
   ;; Modal
   (dialog)])