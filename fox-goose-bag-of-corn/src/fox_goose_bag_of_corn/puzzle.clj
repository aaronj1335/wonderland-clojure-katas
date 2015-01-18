(ns fox-goose-bag-of-corn.puzzle
  (:require [clojure.set :refer [union]]))

(def start-pos [[:fox :goose :corn :you] [:boat] []])

(defn positions
  "s/o to @ponzao http://stackoverflow.com/a/4831131/5377"
  [pred coll]
  (keep-indexed (fn [i x] (when (pred x) i)) coll))

(defn its-cool
  "return true if given a position, nothing's gonna get eaten"
  [pos]
  (not-any? #{#{:fox :goose} #{:goose :corn} #{:fox :goose :corn}} pos))

(defn progress
  "return true if you didn't just undo your last action

  this prevents doing something like bringing the goose to the far shore and
  then right back to the near shore. or the fox for that matter."
  [plan]
  (let [[prev prev2 prev3] (take 3 (reverse plan))]
    (not= prev prev3)))

(defn transitions
  "return all possible transitions from a given position including invalid ones"
  [pos]
  (let [[west boat east] (map #(disj % :you) pos)
        your-pos (nth [:west :boat :east] (first (positions :you pos)))]
    (case your-pos
      :boat [[(union west (conj boat :you)) #{} east]
             [west                          #{} (union east (conj boat :you))]]
      :west (conj (map #(-> [(disj west %) #{:you %} east]) west)
                  [west #{:you} east])
      :east (conj (map #(-> [west #{:you %} (disj east %)]) east)
                  [west #{:you} east]))))

(defn success [plan]
  (= [#{} #{} #{:you :fox :goose :corn}] (last plan)))

(defn search [pos]
  (let [next-plans (fn [plan]
                     (let [pos (last plan)
                           plans (map #(conj plan %) (transitions pos))
                           is-valid (every-pred #(its-cool (last %))
                                                progress)]
                       (filter is-valid plans)))]
    (tree-seq (constantly true) next-plans [pos])))

(defn river-crossing-plan []
  ; since the boat is just kind of noise in the structure, we'll use utility
  ; functions for removing it and adding it back in at the end
  (let [remove-boat (fn [pos]
                      (map #(disj (set %) :boat) pos))
        add-boat (fn [plan]
                   (map (fn [[west boat east]]
                          [west (conj boat :boat) east])
                        plan))]
    (->> start-pos
         (remove-boat)
         (search)
         (filter success)
         (first)
         (add-boat))))
