(ns medical-card.ui.medical-history
  (:require [medical-card.ui.create-event-dialog :refer [new-event-dialog]]))


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