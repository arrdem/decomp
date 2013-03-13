(ns me.arrdem.decomp.core
    (:require [clojure.pprint          :refer [pprint]]
              [me.arrdem.decomp.parser :refer [build-ast]]
              [me.arrdem.decomp.lexer  :refer [html]])
    (:gen-class :main true))


(def process-string
  (comp build-ast html))

(defn -main
  "The only valid arguments are targeted files. If there are no targeted files
then decomp will target stdin as its token source."
  [& args]
  (if-not (empty? args)
    (doseq [f args]
      (pprint (process-string (slurp f)))
      (println ""))

    (do (pprint (process-string (slurp (java.io.BufferedReader. *in*))))
        (println ""))))
