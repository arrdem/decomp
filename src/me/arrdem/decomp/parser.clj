(ns me.arrdem.decomp.parser
    (:require [name.choi.joshua.fnparse :as fnp]))

(defmacro deftoken [symbol val]
  `(def ~symbol
     (fnp/term
      #(= (:lexington.tokens/type %1) ~val))))

(deftoken strtok    :string)
(deftoken wordtok   :word)
(deftoken assignop  :assign)
(deftoken opentok   :open)
(deftoken closetok  :close)
(deftoken cclosetok :cclose)
(deftoken chrtok    :chr)

(def chr
  (fnp/semantics
   chrtok
   (fn [c] (:val c))))

(def word
  (fnp/semantics
   wordtok
   (fn [c] (:val c))))

(def html-kv-pair
  (fnp/semantics
   (fnp/conc
    wordtok
    assignop
    strtok)
   ;; prn))
  (fn [[w _ s]] {(keyword (:val w)) (:val s)})))

(def html-opentag
  (fnp/semantics
   (fnp/conc
    opentok
    wordtok
    (fnp/rep* html-kv-pair)
    closetok)
   ;; prn))
  (fn [[_ name vals __]]
    [(keyword (:val name)) (or (reduce merge vals) {})])))

(def html-closetag
  (fnp/conc
   cclosetok
   wordtok
   closetok))

(declare html-ast)

(defn reduce-strs [s]
  (reduce (fn [acc n]
            (cond (and (string? (last acc))
                       (string? n))
                      (concat (butlast acc)
                              [(str (last acc) " " n)])

                  (or (and (string? (last acc))
                           (char? n))
                      (and (char? (last acc))
                           (string? n))
                      (and (char? (last acc))
                           (char? n)))
                      (concat (butlast acc)
                              [(str (last acc) n)])

                   (vector? n) (concat acc n)

                  :else
                      (concat acc [n])))
          [] s))

(def html-ast
  (fnp/rep+
   (fnp/semantics
    (fnp/conc
     html-opentag
     (fnp/rep*
      (fnp/alt
       word
       chr
       html-ast))
     html-closetag)
    (fn [[o v _]]
      (apply vector (concat o (reduce-strs v)))))))

(defn build-ast [toks]
  (fnp/rule-match
   html-ast
   #(println "FAILED: " %)
   #(println "LEFTOVER: " %2)
   {:remainder toks}))
