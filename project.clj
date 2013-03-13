(defproject me.arrdem.decomp "0.1.0"
  :description "an HTML to Compojure translator"
  :url "http://github.com/arrdem/decomp"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure   "1.4.0"]
                 [org.clojure/tools.cli "0.2.2"]
                 [factual/fnparse       "2.3.0"]
                 [lexington             "0.1.1"]]
  :main me.arrdem.decomp.core)
