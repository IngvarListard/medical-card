(ns medical-card.views.create-event.submit)


(defmulti prepare-form 
  (fn [{:keys [object-type]}]
    object-type))

(defmethod prepare-form "research"
 [form]
 )


(defmethod prepare-form "event"
 [form]
 )


(defmethod prepare-form "document"
 [form]
 )


(defn do-create-event
  [form-data]
  )