(ns medical-card.ui.forms.research
  (:require [rum.core :as r]
            [medical-card.ui.create-event-dialog :refer [formm]]))


(r/defc research-form []
  (formm
   [:div
    [:div {:class "row mb-3"}
     [:label {:for "research-name", :class "col-sm-2 col-form-label"} "Название"]
     [:div
      {:class "col-sm-10"}
      [:input
       {:id "research-name"
        :name "research-name"
        :type "text"
     ;;:class "form-control-plaintext"
        :value ""}]]]
    [:div {:class "row mb-3"}
     [:label {:for "research-desc", :class "col-sm-2 col-form-label"} "Описание"]
     [:div
      {:class "col-sm-10"}
      [:input
       {:id "research-desc"
        :name "research-desc"
        :type "text"
     ;;:class "form-control-plaintext"
        :value ""}]]]]))