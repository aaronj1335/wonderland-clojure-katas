(ns fox-goose-bag-of-corn.puzzle-test
  (:require [clojure.test :refer :all]
            [clojure.pprint :refer [pprint]]
            [fox-goose-bag-of-corn.puzzle :refer :all]))

(def solution1
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

(def solution2
  [[[:fox :goose :corn :you]  [:boat]             []]
   [[:fox :corn]              [:boat :you :goose] []]
   [[:fox :corn]              [:boat]             [:goose :you]]
   [[:fox :corn]              [:boat :you]        [:goose]]
   [[:fox :corn :you]         [:boat]             [:goose]]
   [[:corn]                   [:fox :boat :you]   [:goose]]
   [[:corn]                   [:boat]             [:goose :you :fox]]
   [[:corn]                   [:boat :you :goose] [:fox]]
   [[:corn :goose :you]       [:boat]             [:fox]]
   [[:goose]                  [:boat :you :corn]  [:fox]]
   [[:goose]                  [:boat]             [:fox :you :corn]]
   [[:goose]                  [:boat :you]        [:fox :corn]]
   [[:goose :you]             [:boat]             [:fox :corn]]
   [[]                        [:boat :you :goose] [:fox :corn]]
   [[]                        [:boat]             [:fox :corn :goose :you]]])

(deftest test-its-cool
  (testing "it's not cool when the fox is with that goose tho"
    (is (not (its-cool [#{:fox :goose} #{:boat :you :corn} #{}]))))
  (testing "it's not cool when the goose is with the corn tho"
    (is (not (its-cool [#{:fox} #{:boat :you} #{:corn :goose}]))))
  (testing "it's not cool when they're all together without you"
    (is (not (its-cool [#{:fox :goose :corn} #{:boat :you} #{}]))))
  (testing "be it chill tho? yes it do."
    (is (every? its-cool (map #(map set %) solution1))))
  (testing "be it still chill tho? yes it still do."
    (is (every? its-cool (map #(map set %) solution2)))))

(deftest test-river-crossing-plan
  (testing "the fox, goose, corn, and you all made it to the other side of the river"
    (is (or (= (map #(vec (map set %)) solution1) (river-crossing-plan))
            (= (map #(vec (map set %)) solution2) (river-crossing-plan))))))

