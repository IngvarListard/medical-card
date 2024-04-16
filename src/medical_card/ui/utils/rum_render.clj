(ns medical-card.ui.utils.rum-render
  (:require [rum.core :as r]
            [rum.server-render :as sr]))


(def ^:dynamic *unsafe-attrs* #{})


(defn render-attr-str! [sb attr value & {:keys [unsafe]}]
  (println "unsafe" unsafe "unsafe-attrs" *unsafe-attrs*)
  (let [attr-str (-> value sr/to-str (#(if unsafe % (sr/escape-html %))))]
    (sr/append! sb " " attr "=\"" attr-str "\"")))


(defn render-attr! [tag key value sb]
  (let [attr (sr/normalize-attr-key key)]
    (cond
      (= "type" attr)  :nop ;; rendered manually in render-element! before id
      (= "style" attr) (sr/render-style! value sb)
      (= "key" attr)   :nop
      (= "ref" attr)   :nop
      (= "class" attr) :nop
      (and (= "value" attr)
           (or (= "select" tag)
               (= "textarea" tag))) :nop
      (.startsWith attr "aria-") (render-attr-str! sb attr value)
      (not value)      :nop
      (true? value)    (sr/append! sb " " attr "=\"\"")
      (.startsWith attr "on") (if (string? value)
                                (render-attr-str! sb attr value)
                                :nop)
      (= "dangerouslySetInnerHTML" attr) :nop
      :else (render-attr-str!
             sb attr value
             :unsafe (some #{attr} *unsafe-attrs*)))))


(defn render-static-markup-unsafe
  [element & {:keys [unsafe-attrs]}]
  (binding [*unsafe-attrs* unsafe-attrs]
    (with-redefs [sr/render-attr! render-attr!]
      (r/render-static-markup element))))
