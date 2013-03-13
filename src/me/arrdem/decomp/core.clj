(ns me.arrdem.decomp.core
    (:require [clojure.pprint          :refer [pprint]]
              [me.arrdem.decomp.parser :refer [build-ast]]
              [me.arrdem.decomp.lexer  :refer [html]]
              [clojure.tools.cli       :refer [cli]])
    (:gen-class :main true))

(defn process-string [s]
  (pprint (build-ast (html s))))


(defn -main
  "The only valid arguments are targeted files. If there are no targeted files
then decomp will target stdin as its token source."
  [& args]
  (if-not (empty? args)
    (doseq [f args]
      (pprint (build-ast (html (slurp f)))))

    (pprint (build-ast (html (slurp (java.io.BufferedReader. *in*)))))))
