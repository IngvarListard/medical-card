(defproject medical-card "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-core "1.11.0"]
                 [ring/ring-jetty-adapter "1.11.0"]
                 [compojure "1.7.1"]
                 [ring/ring-devel "1.12.0"]
                 [ring/ring-json "0.5.1"]
                 [rum "0.12.11"]
                 [com.github.seancorfield/next.jdbc "1.3.925"]
                 [org.xerial/sqlite-jdbc "3.43.0.0"]
                 [migratus "1.5.5"]
                 [com.fzakaria/slf4j-timbre "0.4.1"]
                 [com.github.seancorfield/honeysql "2.6.1126"]
                 [cheshire "5.12.0"]]
  :plugins [[lein-ring "0.12.6"]
            [migratus-lein "0.7.3"]]
  :ring {:auto-reload? true
         ;;:handler myproject.core/app
         :open-browser? false
         :reload-paths ["src/" "resources/"]}
  :main ^:skip-aot medical-card.core
  :target-path "target/%s"
  :source-paths ["src"],
  :migratus {:store :database
             :migration-dir "migrations"
             :db {:dbtype "sqlite"
                  :dbname "resources/database/db.sqlite3"}}
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
