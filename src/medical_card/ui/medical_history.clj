(ns medical-card.ui.medical-history
  (:require [rum.core :as r]))


(defn new-event-dialog []
  [:div
   ;; Button trigger modal
   [:button
    {:type "button",
     :class "btn btn-primary",
     :data-bs-toggle "modal",
     :data-bs-target "#new-event"}
    "Добавить запись"]
   ;; Modal
   [:div
    {:class "modal fade",
     :id "new-event",
     :tabindex "-1",
     :aria-labelledby "new-event-label",
     :aria-hidden "true"}
    [:div
     {:class "modal-dialog"}
     [:div
      {:class "modal-content"}
      [:div
       {:class "modal-header"}
       [:h1
        {:class "modal-title fs-5", :id "new-event-label"}
        "Modal title"]
       [:button
        {:type "button",
         :class "btn-close",
         :data-bs-dismiss "modal",
         :aria-label "Close"}]]
      [:div {:class "modal-body"} 
       [:form {:hx-post "/api/create-event"}
        [:input
         {:id "test"}]
        ]]
      [:div
       {:class "modal-footer"}
       [:button
        {:type "button",
         :class "btn btn-secondary",
         :data-bs-dismiss "modal"}
        "Close"]
       [:button
        {:type "button", :class "btn btn-primary"}
        "Save changes"]]]]]])

(defn history-table [documents]
  [:div
   ;; [:button {:hx-post "/clicked", :hx-swap "outerHTML"} "Click Me"]
   (new-event-dialog)
   [:table {:class "table table-hover text-center text-nowrap"}
    [:thead
     [:tr
      [:th {:scope "col"} "Событие"]
      [:th {:scope "col"} "Врач"]
      [:th {:scope "col"} "Дата"]
      [:th {:scope "col"} "Документ"]
      [:th {:scope "col"} "Организация"]]]
    (let [tbody (for [doc documents]
                  (-> doc
                      (map [:events/name
                            :doctors/name
                            :events/created_at
                            :documents/name
                            :organizations/name])
                      ((fn [rows] (vec (into [:tr] (for [td rows] [:td td])))))))]
      (into [:tbody] tbody))]])


(comment
  (history-table [{:events/name "event" :doctors/name "doctor name" :events/created_at "time" :documents/name "document name" :organizations/name "organization name"}
                  {:events/name "event" :doctors/name "doctor name" :events/created_at "time" :documents/name "document name" :organizations/name "organization name"}
                  {:events/name "event" :doctors/name "doctor name" :events/created_at "time" :documents/name "document name" :organizations/name "organization name"}
                  {:events/name "event" :doctors/name "doctor name" :events/created_at "time" :documents/name "document name" :organizations/name "organization name"}
                  {:events/name "event" :doctors/name "doctor name" :events/created_at "time" :documents/name "document name" :organizations/name "organization name"}
                  {:events/name "event" :doctors/name "doctor name" :events/created_at "time" :documents/name "document name" :organizations/name "organization name"}
                  {:events/name "event" :doctors/name "doctor name" :events/created_at "time" :documents/name "document name" :organizations/name "organization name"}])

  (concat [1 2 3] [4 5 6])

  (for [i [1 2 3 4 5]]
    (-> i))

  (new-event-dialog)
  :rfc)