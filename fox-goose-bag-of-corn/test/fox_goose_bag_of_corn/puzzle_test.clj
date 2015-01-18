(ns fox-goose-bag-of-corn.puzzle-test
  (:require [clojure.test :refer :all]
            [fox-goose-bag-of-corn.puzzle :refer :all]))

(def solution
  [[[:fox :goose :corn :you]  [:boat]             []]
   [[:fox :corn]              [:boat :you :goose] []]
   [[:fox :corn]              [:boat]             [:goose :you]]
   [[:fox :corn]              [:boat :you]        [:goose]]
   [[:fox :corn :you]         [:boat]             [:goose]]
   [[:fox]                    [:corn :boat :you]  [:goose]]
   [[:fox]                    [:boat]             [:goose :you :corn]]
   [[:fox]                    [:boat :you :goose] [:corn]]
   [[:fox :goose :you]        [:boat]             [:corn]]
   [[:goose]                  [:boat :you :fox]   [:corn]]
   [[:goose]                  [:boat]             [:corn :you :fox]]
   [[:goose]                  [:boat :you]        [:corn :fox]]
   [[:goose :you]             [:boat]             [:corn :fox]]
   [[]                        [:boat :you :goose] [:corn :fox]]
   [[]                        [:boat]             [:corn :fox :goose :you]]])

(deftest test-its-cool-chill
  (testing "it's not cool when the fox is with that goose tho"
    (is (not (its-cool-chill [[:fox :goose] [:boat :you :corn] []]))))
  (testing "it's not cool when the goose is with the corn tho"
    (is (not (its-cool-chill [[:fox] [:boat :you] [:corn :goose]]))))
  (testing "be it chill tho? yes it do."
    (is (every? its-cool-chill solution))))

; (deftest test-one-way-boat
;   (testing "you don't get off the boat the same shore than you got on"
;     (is (not (one-way-boat [[#{:goose :corn :you :fox} #{:boat} #{}]
;                             [#{:fox :corn} #{:you :boat :goose} #{}]
;                             [#{:goose :corn :you :fox} #{:boat} #{}]])))

;     (is (not (one-way-boat [[#{:corn} #{:boat} #{:you :fox :goose}]
;                             [#{:corn} #{:you :boat :goose} #{:fox}]
;                             [#{:corn} #{:boat} #{:you :fox :goose}]]))))
;   (testing "you get off the boat a different shore you got on"
;     (is (not (one-way-boat [[#{:goose :corn :you :fox} #{:boat} #{}]
;                             [#{:fox :corn} #{:you :boat :goose} #{}]
;                             [#{:corn :fox} #{:boat} #{:you :goose}]])))

;     (is (not (one-way-boat [[#{:corn} #{:boat} #{:you :fox :goose}]
;                             [#{:corn} #{:you :boat :goose} #{:fox}]
;                             [#{:corn :you :goose} #{:boat} #{:fox}]])))))

(deftest test-transitions
  (testing "transitions from the left shore"
    (is (= (transitions (map set (first solution)))
           '([#{:goose :corn} #{:you :fox :boat} #{}]
             [#{:fox :corn} #{:you :boat :goose} #{}]
             [#{:fox :goose} #{:you :boat :corn} #{}]))))
  (testing "transitions from the boat"
    (is (= (transitions (map set (second solution)))
           '([#{:goose :corn :fox :you} #{:boat} #{}]
             [#{:fox :corn} #{:boat} #{:you :goose}]))))
  (testing "transitions from the right shore"
    (is (= (transitions (map set (nth solution 2)))
           '([#{:fox :corn} #{:you :boat :goose} #{}])))))


(run-tests)

; (deftest test-river-crossing-plan
;   (testing "the fox, goose, corn, and you all made it to the other side of the river"
;     (is (= (map set solution)
;            (map set (river-crossing-plan))))))
