(ns medical-card.views.ajax
  (:require [rum.core :as rum]
            [ring.util.response :as r]
            [medical-card.ui.create-event-dialog :as f]
            [medical-card.ui.forms.research :refer [research-form]]))


(defn get-create-event-view []
  (-> (f/formm)
      rum/render-html
      r/response
      (r/header "HX-Trigger-After-Settle" "load-object-form")))


(defn get-record-form-view
  [object-for]
  ;; TODO: когда-нибудь здесь будет мультиметод
  (case object-for
    ;; вместо строки вставить откуда-нибудь из констант
    "research" (rum/render-html (research-form))
    (throw (Exception. "Неизвестная форма"))))
