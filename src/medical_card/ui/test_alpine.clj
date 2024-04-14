(ns medical-card.ui.test-alpine
  (:require [medical-card.ui.core :refer [->js]]
            [rum.core :as r]))

(defn calendar
  []
  [:div.antialiased.sans-serif.bg-gray-100
   [:div {:x-data "app()"}]])


(r/defc test-alpine
  []
  [:div
   [:div
    {:x-data "app()"}
    [:input {:x-model "search" :placeholder "Search..."}]
    [:ul
     [:template
      {:x-for "item in filteredItems" ":key" "item"}
      [:li {:x-text "item"}]]]]
   [:script
    {:dangerouslySetInnerHTML
     {:__html
      (->js
       '(defn app []
          {:search ""
           :filteredItems
           (fn []
             (this-as
              self
              (.filter self.items (fn [i] (.startsWith i self.search)))))
           :items ["foo", "bar", "baz"]}))}}]])
