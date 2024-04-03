(ns medical-card.db.tables
  (:require [medical-card.common :refer [defs]]))

#_{:clj-kondo/ignore [:unused-binding]}
(defs
  [documents :documents
   doctors :doctors
   users :users
   organizations :organizations
   specializations :specializations
   events :events
   event_types :event_types
   doctors_to_organizations :doctors_to_organizations
   doctors_to_specializations :doctors_to_specializations
   research_types :research_types
   researches :researches])
