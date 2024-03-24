(ns medical-card.ui.core
  (:require [rum.core :as r]))


(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   [:title "test title"]
   [:link {:href "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" :rel "stylesheet" :integrity "sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" :crossorigin "anonymous"}]
   [:script {:src "https://cdn.jsdelivr.net/npm/alpinejs@3.x.x/dist/cdn.min.js" :defer ""}]])


(defn body [content]
  [:body
   [:script {:src "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" :integrity "sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" :crossorigin "anonymous"}]
   [:script {:src "https://unpkg.com/htmx.org@1.9.11"}]
   [:h1 "Hello, world"]
   content])


(r/defc page [content]
  [:!DOCTYPE
   {:lang "ru" :html ""}
   (head)
   (body content)])