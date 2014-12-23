(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def words (-> "words.edn"
               (io/resource)
               (slurp)
               (read-string)))

(defn valid-next-word [word other]
  (if (not= (count word) (count other))
    false
    (->> (map vector word other)
         (filter #(apply not= %))
         (count)
         (= 1))))

(defn search [word1 word2]
  (let [branch? (fn [doublet]
                  not= (last doublet) word2)
        children (fn [doublet]
                   (->> words
                        (filter #(and (valid-next-word (last doublet) %)
                                      (not-any? (partial = %) doublet)))
                        (map #(conj doublet %))))]
    (tree-seq branch? children [word1])))

(defn doublets [word1 word2]
  (->> (search word1 word2)
      (filter #(= (last %) word2))
      (first)))
