(ns medical-card.views.ajax
  (:require [rum.core :as rum]
            [ring.util.response :as r]
            [medical-card.ui.forms.research :refer [research-form]]))


(defn get-create-event-view []
  (-> (research-form)
      rum/render-html
      r/response))


(defn get-record-form-view
  [object-for]
  (-> object-for
      research-form
      rum/render-html
      r/response))

