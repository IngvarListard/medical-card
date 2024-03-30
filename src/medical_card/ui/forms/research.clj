(ns medical-card.ui.forms.research
  (:require [rum.core :as r]
            [medical-card.ui.create-event-dialog :refer [create-record-selector-form]]
            [medical-card.constants :as const]
            [medical-card.schemas.event :refer [Research get-top-level-subschemas schema-entry-to-form-params]]))


(def ^:const input-types
  {:string "text"})

(def form-to-schema
  {:research Research})

(comment
  (= Research (:research form-to-schema))
  :rcf)


(defmulti create-record-form (fn [val] val))


(defmethod create-record-form "research"
  [_]
  [:div
   [:div {:class "row mb-3"}
    [:label {:for "research-name", :class "col-sm-2 col-form-label"}
     "Название"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-name"
       :name "research-name"
       :type "text"
       :value ""}]]]
   [:div {:class "row mb-3"}
    [:label {:for "research-desc", :class "col-sm-2 col-form-label"}
     "Описание"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-desc"
       :name "research-desc"
       :type "text"
       :value ""}]]]
   [:div {:class "row mb-3"}
    [:label {:for "research-name", :class "col-sm-2 col-form-label"}
     "Тип"]
    [:div
     {:class "col-sm-10"}
     [:input
      {:id "research-name"
       :name "research-name"
       :type "text"
       :value ""}]]]
   [:div {:class "row mb-3"}
    [:label {:for "research-name", :class "col-sm-2 col-form-label"}
     "Дата"]
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
    [:div
     [:label {:for "research-name", :class "col-sm-2 col-form-label"} "Тип"]
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
                       (get-top-level-subschemas $ [:name :description :type :start_date])
                       (map schema-entry-to-form-params $))]
     (-> schemas-col
         inputs-from-schema
         (create-record-selector-form :value val)))))


(comment
  ;; (schema-entry-to-form-params (get-top-level-subschemas Research))
  (map schema-entry-to-form-params (get-top-level-subschemas Research [:name]))
  (inputs-from-schema [{:name "testname" :type :string :display-name "Поле!"}])
  (research-form)
  :rcf)