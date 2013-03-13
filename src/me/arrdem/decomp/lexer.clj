(ns me.arrdem.decomp.lexer
    (:require [lexington.lexer :refer :all]
              [lexington.utils.lexer :refer :all]))

(deflexer html-base
  :cclose  "</"
  :com-open  "<!--"
  :com-close "-->"
  :open    "<"
  :close   ">"
  :assign  "="
  :string  #"\"[^\"]+\""
  :word    #"[^ \t\r\n=\"\\<>]+"
  :ws      #" |\t|\r|\n"
  :chr     #".")

(def wordfn (fn [v] (apply str (:lexington.tokens/data v))))
(def strfn (fn [v] (apply str (drop 1 (butlast (:lexington.tokens/data v))))))

(def html
  (-> html-base
      (discard :ws)
      ;; (with-string :str :only [:integer])
      (generate-for :word    :val wordfn)
      (generate-for :string  :val strfn)
      (generate-for :chr     :val wordfn)
      ;; (with-string  :str     :only [:integer])
      ))
