(ns medical-card.views.index-page
  (:require [medical-card.db.queries :refer [get-documents]]
            [medical-card.ui.core :refer [page]]
            [medical-card.ui.pages.medical-history :refer [history-table]]))


(defn medical-history-view []
  (let [docs (get-documents)]
    (page (history-table docs))))

(comment
  (medical-history-view)
  (let [docs (get-documents)]
    (page (history-table docs)))

  (for [i (get-documents)]
    (map i [:events/name
            :doctors/name
            :events/created_at
            :documents/name
            :organizations/name]))
  (map (get-documents) [:events/name
                        :doctors/name
                        :events/created_at
                        :documents/name
                        :organizations/name])

  :rfc)
