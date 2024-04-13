(ns medical-card.ui.test-alpine
  (:require [medical-card.ui.core :refer [->js]]
            [rum.core :as r]
            [hiccup2.core :as h]
            [clojure.string :as s]))

(defn calendar
  []
  [:div.antialiased.sans-serif.bg-gray-100
   [:div {:x-data "app()"}]])

(comment
  (r/render-static-markup
   [:div
    {:dangerouslySetInnerHTML
     {:__html
      (str
       "x-data="
       (s/replace
        (->js
         '{:search ""
           "filteredItems" (fn [] (js/console.log js/this.items.filter) js/this.items)
           :items ["foo", "bar", "baz"]})
        #"\""
        "'")
       (r/render-static-markup [:input {:x-model "search", :placeholder "Search..."}])
       (r/render-static-markup [:ul [:template {:x-for "item in filteredItems", ":key" "item"}]]))}}])
  :rcf)


(r/defc test-alpine
  []
  [:div
   {:dangerouslySetInnerHTML
    {:__html
     (str
      "<div x-data=\""
      (s/replace
       (->js
        '{:search ""
          :filteredItems (fn []
                           (console.log "search" js/this.search)
                           (def ctx {:search js/this.search
                                     :items js/this.items})
                           (.filter (:items ctx) (fn [i] (.startsWith i (:search)))))
          :items ["foo", "bar", "baz"]})
       #"\""
       "'")
      "\">"
      (r/render-static-markup [:input {:x-model "search" :placeholder "Search..."}])
      (r/render-static-markup [:ul
                               [:template
                                {:x-for "item in filteredItems" ":key" "item"}
                                [:li {:x-text "item"}]]])
      "
</div>
       ")}}])

(comment
  (println (->js '((defn app []
                     {:init (fn [] "asdf")
                      :test (fn [] "12")}))))

  (->js '(^:=> fn [x ^js {:keys [y]}] (+ x y)))
  ; function (x, p__35427) {
  ; return (function () {
  ;  let map__12 = p__35427;
  ; let y3 = squint_core.get(map__12, "y");
  ; return (x) + (y3)
  ; })()
  ; };
  
  ; Documentation's example
  (->js '(fn ^:=> [] 1))
  ; "function () {\nreturn 1\n};\n"
  :rcf)