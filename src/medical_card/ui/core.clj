(ns medical-card.ui.core
  (:require [rum.core :as r]
            [squint.compiler :as squint]
            [cheshire.core :as json]))


(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   [:title "test title"]
  ;;  [:link {:href "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" :rel "stylesheet" :integrity "sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" :crossorigin "anonymous"}]
   [:link
    {:href "https://cdn.jsdelivr.net/npm/daisyui@4.10.1/dist/full.min.css",
     :rel "stylesheet",
     :type "text/css"}]
   [:script {:src "https://cdn.tailwindcss.com"}]
   [:script {:src "https://cdn.jsdelivr.net/npm/alpinejs@3.x.x/dist/cdn.min.js" :defer "true"}]
   [:script {:type "importmap"
             :dangerouslySetInnerHTML
             {:__html
              (json/generate-string
               {:imports
                {"squint-cljs/src/squint/core.js" "https://cdn.jsdelivr.net/npm/squint-cljs@0.4.81/src/squint/core.js"}})}}]
   [:script {:type "module"
             :dangerouslySetInnerHTML {:__html
                                       "globalThis.squint_core = await import('squint-cljs/src/squint/core.js');"}}]])


(defn body [content]
  [:body
  ;;  [:script {:src "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" :integrity "sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" :crossorigin "anonymous"}]
   [:script {:src "https://unpkg.com/htmx.org@1.9.11"}]
   [:h1 "Hello, world"]
   content])


(r/defc page [content]
  [:!DOCTYPE
   {:lang "ru" :html ""}
   (head)
   (body content)])


(def state (atom nil))


(defn ->js [form]
  (let [res (squint.compiler/compile-string* (str form))]
    (reset! state res)
    (:body res)))

(comment
  ;; ->js example
  (->js '(let [elt (js/document.getElementById "counter")
               val (-> (.-innerText elt) parse-long)]
           (set! elt -innerText (inc val))))

  :rcf)