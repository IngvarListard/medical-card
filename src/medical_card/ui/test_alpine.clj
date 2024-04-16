(ns medical-card.ui.test-alpine
  (:require [medical-card.ui.core :refer [->js page]]
            [medical-card.ui.utils.rum-render :refer [render-static-markup-unsafe]]))


(defn arrow-btn
  [& {:keys [x-class disabled click svg-d]}]
  [:button
   {:type "button"
    :class "leading-none rounded-lg transition ease-in-out duration-100 inline-flex cursor-pointer hover:bg-gray-200 p-1 items-center"
    ":class" (or x-class "{'cursor-not-allowed opacity-25': month == 0 }")
    ;; :disabled (or disabled "month == 0 ? true : false")
    :x-on:click (or click "month--; getNoOfDays()")}
   [:svg.h-6.w-6.text-gray-500.inline-flex.leading-none
    {:fill "none"
     :viewBox "0 0 24 24"
     :stroke "currentColor"}
    [:path {:stroke-linecap "round"
            :stroke-linejoin "round"
            :stroke-width "2"
            :d (or svg-d "M15 19l-7-7 7-7")}]]])


(defn modal
  []
  [:div {:style {:background-color "rgba(0, 0, 0, 0.8)"}
         :class "fixed z-40 top-0 right-0 left-0 bottom-0 h-full w-full"
         :x-show "openEventModal"
         :x-transition:enter "transition ease-out duration-300"
         :x-transition:enter-start "opacity-0"}
   [:div {:class "p-4 max-w-xl mx-auto relative absolute left-0 right-0 overflow-hidden mt-24"}
    [:div {:class "shadow absolute right-0 top-0 w-10 h-10 rounded-full bg-white text-gray-500 hover:text-gray-800 inline-flex items-center justify-center cursor-pointer"
           :x-on:click "openEventModal = !openEventModal"}
     [:svg {:class "fill-current w-6 h-6" :xmlns "http://www.w3.org/2000/svg" :viewBox "0 0 24 24"}
      [:path {:d "M16.192 6.344L11.949 10.586 7.707 6.344 6.293 7.758 10.535 12 6.293 16.242 7.707 17.656 11.949 13.414 16.192 17.656 17.606 16.242 13.364 12 17.606 7.758z"}]]]
    [:div {:class "shadow w-full rounded-lg bg-white overflow-hidden w-full block p-8"}
     [:h2 {:class "font-bold text-2xl mb-6 text-gray-800 border-b pb-2"}
      "Add Event Details"]
     [:div {:class "mb-4"}
      [:label {:class "text-gray-800 block mb-1 font-bold text-sm tracking-wide"} "Event title"]
      [:input {:class "bg-gray-200 appearance-none border-2 border-gray-200 rounded-lg w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-blue-500"
               :type "text"
               :x-model "event_title"}]]
     [:div {:class "mb-4"}
      [:label {:class "text-gray-800 block mb-1 font-bold text-sm tracking-wide"} "Event date"]
      [:input {:class "bg-gray-200 appearance-none border-2 border-gray-200 rounded-lg w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-blue-500"
               :type "text"
               :x-model "event_date"
               :readonly "true"}]]
     [:div {:class "inline-block w-64 mb-4"}
      [:label {:class "text-gray-800 block mb-1 font-bold text-sm tracking-wide"} "Select a theme"]
      [:div {:class "relative"}
       [:select {:x-on:change "event_theme = $event.target.value;"
                 :x-model "event_theme"
                 :class "block appearance-none w-full bg-gray-200 border-2 border-gray-200 hover:border-gray-500 px-4 py-2 pr-8 rounded-lg leading-tight focus:outline-none focus:bg-white focus:border-blue-500 text-gray-700"}
        [:template {:x-for "(theme, index) in themes"}
         [:option {:value "theme.value" :x-text "theme.label"}]]]
       [:div {:class "pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700"}
        [:svg {:class "fill-current h-4 w-4" :xmlns "http://www.w3.org/2000/svg" :viewBox "0 0 20 20"}
         [:path {:d "M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"}]]]]]
     [:div {:class "mt-8 text-right"}
      [:button {:type "button"
                :class "bg-white hover:bg-gray-100 text-gray-700 font-semibold py-2 px-4 border border-gray-300 rounded-lg shadow-sm mr-2"
                :x-on:click "openEventModal = !openEventModal"}
       "Cancel"]
      [:button {:type "button"
                :class "bg-gray-800 hover:bg-gray-700 text-white font-semibold py-2 px-4 border border-gray-700 rounded-lg shadow-sm"
                :x-on:click "addEvent()"}
       "Save Event"]]]]])


(defn calendar*
  []
  [:div {:class "antialiased sans-serif bg-gray-100"}
   [:div {:x-data "app()" :x-cloack "true"}
    [:div {:class "container mx-auto px-4 py-2 md:py-24"}
     [:div {:class "bg-white rounded-lg shadow overflow-hidden"}
      [:div {:class "flex items-center justify-between py-2 px-6"}
       [:div
        [:span {:x-text "MONTH_NAMES[month]"
                :class "text-lg font-bold text-gray-800"}]
        [:span {:x-text "year"
                :class "ml-1 text-lg text-gray-600 font-normal"}]]
       [:div {:class "border rounded-lg px-1"
              :style {:padding-top "2px"}}
        (arrow-btn)
        [:div {:class "border-r inline-flex h-6"}]
        (arrow-btn
         :x-class "{'cursor-not-allowed opacity-25': month == 11 }"
        ;;  :disabled "month == 11 ? true : false"
         :click "month++; getNoOfDays()"
         :svg-d "M9 5l7 7-7 7")]]

      [:div {:class "-mx-1 -mb-1"}
       [:div {:class "flex flex-wrap" :style {:margin-bottom "-40px;"}}
        [:template {:x-for "(day, index) in DAYS" :key "index"}
         [:div {:style {:width "14.26%"} :class "px-2 py-2"}
          [:div {:x-text "day"
                 :class "text-gray-600 text-sm uppercase tracking-wide font-bold text-center"}]]]]
       [:div {:class "flex flex-wrap border-t border-l"}
        [:template {:x-for "blankday in blankdays"}
         [:div
          {:style {:width "14.28%" :height "120px"}
           :class "text-center border-r border-b px-4 pt-2"}]]
        [:template {:x-for "(date, dateIndex) in no_of_days" :keys "dateIndex"}
         [:div {:style {:width "14.28%" :height "120px"}
                :class "px-4 pt-2 border-r border-b relative"}
          [:div {:x-on:click "showEventModal(date)"
                 :x-text "date"
                 :class "inline-flex w-6 h-6 items-center justify-center cursor-pointer text-center leading-none rounded-full transition ease-in-out duration-100"
                 ":class" "{'bg-blue-500 text-white': isToday(date) == true, 'text-gray-700 hover:bg-blue-200': isToday(date) == false }"}]
          [:div {:style {:height "80px"}
                 :class "overflow-y-auto mt-1"}
           [:template {:x-for "event in events.filter(e => new Date(e.event_date).toDateString() ===  new Date(year, month, date).toDateString())"}
            [:div {:class "px-2 py-1 rounded-lg mt-1 overflow-hidden border"
                   ":class" "{
												'border-blue-200 text-blue-800 bg-blue-100': event.event_theme === 'blue',
												'border-red-200 text-red-800 bg-red-100': event.event_theme === 'red',
												'border-yellow-200 text-yellow-800 bg-yellow-100': event.event_theme === 'yellow',
												'border-green-200 text-green-800 bg-green-100': event.event_theme === 'green',
												'border-purple-200 text-purple-800 bg-purple-100': event.event_theme === 'purple'
											}"}
             [:p {:x-text "event.event_title"
                  :class "text-sm truncate leading-tight"}]]]]]]]]]]
    (modal)]


   [:script
    {:dangerouslySetInnerHTML
     {:__html
      (->js
       '(do
          (def MONTH_NAMES ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"])
          (def DAYS ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"])
          (defn app []
            {:init (fn []
                     (this.initDate)
                     (this.getNoOfDays))
             :month ""
             :year ""
             :no_of_days []
             :blankdays []

             :events
             [{:event_date (new Date 2020 3 1)
               :event_title "April Fool's Day"
               :event_theme "blue"}
              {:event_date (new Date 2020 3 10)
               :event_title "Birthday"
               :event_theme "red"}
              {:event_date (new Date 2020 3 16)
               :event_title "Upcoming Event"
               :event_theme "green"}]
             :event_title ""
             :event_date ""
             :event_theme "blue"

             :themes
             [{:value "blue" :label "Blue Theme"}
              {:value "red" :label "Red Theme"}
              {:value "yellow" :label "Yellow Theme"}
              {:value "green" :label "Green Theme"}
              {:value "purple" :label "Purple Theme"}]

             :openEventModal false

             :initDate (fn []
                         (this-as
                          $
                          (let [today (new Date)
                                m (.getMonth today)
                                y (.getFullYear today)
                                t (.getDate today)]
                            (set! $.month m)
                            (set! $.year y)
                            (set! $.datepickerValue (-> (new Date y m t) .toDateString)))))
             :isToday (fn [date]
                        (this-as
                         $
                         (let [today (new Date)
                               d (new Date $.year $.month date)]
                           (identical? (-> today .toDateString) (-> d .toDateString)))))
             :showEventModal (fn [date]
                               (this-as
                                $
                                (set! $.openEventModal true)
                                (set! $.event_date (-> (new Date $.year $.month $.date) .toDateString))))
             :addEvent (fn []
                         (this-as
                          $
                          (if (= $.event_title "")
                            nil
                            (do
                              (.push $.events {:event_date $.event_date
                                               :event_title $.event_title
                                               :event_theme $.event_theme})

                              (set! $.event_title "")
                              (set! $.event_date "")
                              (set! $.event_theme "blue")

                              (set! $.event_modal false)))))
             :getNoOfDays (fn []
                            (console.log "get no of days")
                            (this-as
                             $
                             (let [daysInMonth (-> (new Date $.year (+ $.month 1) 0) .getDate)
                                   daysOfWeek (-> (new Date $.year $.month) .getDay)
                                   blankDaysArray (vec (range 1 (+ daysOfWeek 1)))
                                   daysArray (vec (range 1 daysInMonth))]
                               (set! $.blankdays blankDaysArray)
                               (set! $.no_of_days daysArray))))})))}}]])


(defn calendar []
  (-> (calendar*)
      page
      (render-static-markup-unsafe
       :unsafe-attrs #{"@click" "x-class" "disabled" ":class" "x-for" "x-on:click"})))


(comment
  (render-static-markup-unsafe
   [:div {"@click" "''{}<>" :x-class "''{}<>" :disabled "''{}<>" ":class" "''{}<>"}]
   :unsafe-attrs #{"@click" "x-class" "disabled" ":class"})
  :rcf)
