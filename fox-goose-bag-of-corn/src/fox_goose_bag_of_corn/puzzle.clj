(ns fox-goose-bag-of-corn.puzzle
  (:require [clojure.pprint :refer [pprint]]))

(def start-pos [[:fox :goose :corn :you] [:boat] []])

(def invalid-groups #{#{:fox :goose} #{:goose :corn}})

(defn its-cool-chill
  "return true if given a position, nothing's gonna get eaten"
  [pos]
  (not-any? invalid-groups (map set pos)))

; (defn one-way-boat
;   "return true if given a plan, you didn't get off the boat on the same shore
;   you left. note this only checks the last position."
;   [plan]
;   (let [last-3-poss (take 3 (reverse plan))
;         [prev prev2 prev3] (map #(first (positions :you %)) last-3-poss)]
;     (or (not= prev2 1) (not= prev3 prev))))

(defn progress
  "return true if you didn't just undo your last action

  this prevents doing something like bringing the goose to the far shore and
  then right back to the near shore."
  [plan]
  (let [[prev prev2 prev3] (take 3 (reverse plan))]
    (not= prev prev3)))

(defn positions
  "s/o to @ponzao http://stackoverflow.com/a/4831131/5377"
  [pred coll]
  (keep-indexed (fn [i x]
                  (when (pred x)
                    i))
                coll))

; (defn transitions
;   "given a position, return the possible positions to which we can transition"
;   [plan]

;   (let [pos (last plan)
;         prev-pos (second (reverse plan))

;         your-idx (first (positions :you pos))
;         prev-idx (when prev-pos (first (positions :you prev-pos)))

;         ; "bringable" is all the items in your group that you can take (i.e.
;         ; everything except you or the boat)
;         bringable (disj (disj (nth pos your-idx) :you) :boat)]

;     ; (print "in transitions\n")
;     ; (pprint {:pos (last plan)
;     ;          :prev-pos (second (reverse plan))

;     ;          :your-idx (first (positions :you pos))
;     ;          :prev-idx (when prev-pos (first (positions :you prev-pos)))

;     ;          :bringable (disj (disj (nth pos your-idx) :you) :boat)
;     ;          })

;     (if (= your-idx 1)

;       (if (= prev-idx 0)
;         [[(nth pos 0) #{:boat} (conj (nth pos 2) :you (first bringable))]]
;         [[(conj (nth pos 0) :you (first bringable)) #{:boat} (nth pos 2)]])

;       (let [new-set (if (= your-idx 0)
;                       (fn [bring]
;                         [(disj bringable bring)
;                          (conj #{:boat :you} bring)
;                          (nth pos 2)])
;                       (fn [bring]
;                         [(nth pos 0)
;                          (conj #{:boat :you} bring)
;                          (disj bringable bring)]))]

;         (map new-set bringable)))))

(defn transitions
  "given a position, return the possible positions to which we can transition"
  [pos]

  (let [
        ; pos (last plan)
        ; prev-pos (second (reverse plan))

        your-idx (first (positions :you pos))
        ; prev-idx (when prev-pos (first (positions :you prev-pos)))

        ; "bringable" is all the items in your location that you can take (i.e.
        ; everything except you or the boat)
        bringable (disj (disj (nth pos your-idx) :you) :boat)]

    ; (print "in transitions\n")
    ; (pprint {:pos (last plan)
    ;          :prev-pos (second (reverse plan))

    ;          :your-idx (first (positions :you pos))
    ;          :prev-idx (when prev-pos (first (positions :you prev-pos)))

    ;          :bringable (disj (disj (nth pos your-idx) :you) :boat)
    ;          })

    (if (= your-idx 1)

      [[(conj (nth pos 0) :you (first bringable)) #{:boat} (nth pos 2)]
       [(nth pos 0) #{:boat} (conj (nth pos 2) :you (first bringable))]]

      (let [new-set (if (= your-idx 0)
                      (fn [bring]
                        [(disj bringable bring)
                         (conj #{:boat :you} bring)
                         (nth pos 2)])
                      (fn [bring]
                        [(nth pos 0)
                         (conj #{:boat :you} bring)
                         (disj bringable bring)]))]

        (map new-set bringable)))))

(defn success [plan]
  (= [#{} #{:boat} #{:you :fox :goose :corn}] (last plan)))

; (pprint (transitions (map set start-pos)))
; (pprint (transitions (map set [[:fox :corn]              [:boat :you :goose] []])))

(defn search [pos]
  (let [
        branch? (complement success)
        ; branch? (fn [plan]
        ;           ; (print "============================================================\n")
        ;           ; (print "BRANCHING ON:\n")
        ;           ; (pprint plan)
        ;           ; (print "------------------------------------------------------------\n")
        ;           (its-cool-chill (last plan)))

        children (fn [plan]
                   (when (not (success plan))
                     (print "============================================================\n")
                     (print "YIELDING CHILDREN FOR:\n")
                     (pprint plan)
                     (print "............................................................\n")
                     (pprint (filter (every-pred #(its-cool-chill (last %))
                                         progress)
                             (map #(conj plan %) (transitions (last plan)))))
                     (print "------------------------------------------------------------\n")
                     (filter (every-pred #(its-cool-chill (last %))
                                         progress)
                             (map #(conj plan %) (transitions (last plan))))
                     ))

        ]
    (tree-seq branch? children [pos])))

(defn river-crossing-plan []
  (first (filter (fn [plan]
                   (print "============================================================\n")
                   (print "GOT PLAN:\n")
                   (pprint plan)
                   (print "------------------------------------------------------------\n")
                   (success plan)
                   ) (search (map set start-pos)))))
