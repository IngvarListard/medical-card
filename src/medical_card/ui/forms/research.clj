(ns medical-card.ui.forms.research
  (:require [rum.core :as r]
            [medical-card.ui.create-event-dialog :refer [create-record-selector-form]]
            [medical-card.constants :as const]))

(defmulti create-record-form (fn [val] val))


(defmethod create-record-form "research"
  [_]
  [:div
   [:div {:class "row mb-3"}
    [:label {:for "research-name", :class "col-sm-2 col-form-label"} "Название"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-name"
       :name "research-name"
       :type "text"
       :value ""}]]]
   [:div {:class "row mb-3"}
    [:label {:for "research-desc", :class "col-sm-2 col-form-label"} "Описание"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-desc"
       :name "research-desc"
       :type "text"
       :value ""}]]]
   [:div {:class "row mb-3"}
    [:label {:for "research-name", :class "col-sm-2 col-form-label"} "Тип"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-name"
       :name "research-name"
       :type "text"
       :value ""}]]]
   [:div {:class "row mb-3"}
    [:label {:for "research-name", :class "col-sm-2 col-form-label"} "Дата"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-name"
       :name "research-name"
       :type "text"
       :value ""}]]]])


(defmethod create-record-form "event"
  [_]
  [:div
   [:div {:class "row mb-3"}
    [:label {:for "research-name" :class "col-sm-2 col-form-label"} "Тип"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-name"
       :name "research-name"
       :type "text"
       :value ""}]]]
   [:div {:class "row mb-3"}
    [:label {:for "research-desc", :class "col-sm-2 col-form-label"} "Имя"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-desc"
       :name "research-desc"
       :type "text"
       :value ""}]]]
   [:div {:class "row mb-3"}
    [:label {:for "research-name", :class "col-sm-2 col-form-label"} "Опиание"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-name"
       :name "research-name"
       :type "text"
       :value ""}]]]
   [:div {:class "row mb-3"}
    [:label {:for "research-name", :class "col-sm-2 col-form-label"} "Предшествующее событие"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-name"
       :name "research-name"
       :type "text"
       :value ""}]]]
   [:div {:class "row mb-3"}
    [:label {:for "research-name", :class "col-sm-2 col-form-label"} "Исследование"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-name"
       :name "research-name"
       :type "text"
       :value ""}]]]])


(defmethod create-record-form "document"
  [_]
  [:div
   [:div {:class "row mb-3"}
    [:label {:for "research-name" :class "col-sm-2 col-form-label"} "Имя"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-name"
       :name "research-name"
       :type "text"
       :value ""}]]]
   [:div {:class "row mb-3"}
    [:label {:for "research-desc", :class "col-sm-2 col-form-label"} "Путь"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-desc"
       :name "research-desc"
       :type "text"
       :value ""}]]]
   [:div {:class "row mb-3"}
    [:label {:for "research-name", :class "col-sm-2 col-form-label"} "Тип"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-name"
       :name "research-name"
       :type "text"
       :value ""}]]]
   [:div {:class "row mb-3"}
    [:label {:for "research-name", :class "col-sm-2 col-form-label"} "Событие"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-name"
       :name "research-name"
       :type "text"
       :value ""}]]]
   [:div {:class "row mb-3"}
    [:label {:for "research-name", :class "col-sm-2 col-form-label"} "Дата"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-name"
       :name "research-name"
       :type "text"
       :value ""}]]]])


(defmethod create-record-form nil
 [_]
 (create-record-form "research"))


(defn research-form
  ([] (research-form nil))
  ([val]
   (create-record-selector-form
    (create-record-form val)
    :value val)))