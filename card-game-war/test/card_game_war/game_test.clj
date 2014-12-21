(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))


(deftest test-ordering
  (is (= ordering {[:spade 2] 0
                   [:club 2] 1
                   [:diamond 2] 2
                   [:heart 2] 3
                   [:spade 3] 4
                   [:club 3] 5
                   [:diamond 3] 6
                   [:heart 3] 7
                   [:spade 4] 8
                   [:club 4] 9
                   [:diamond 4] 10
                   [:heart 4] 11
                   [:spade 5] 12
                   [:club 5] 13
                   [:diamond 5] 14
                   [:heart 5] 15
                   [:spade 6] 16
                   [:club 6] 17
                   [:diamond 6] 18
                   [:heart 6] 19
                   [:spade 7] 20
                   [:club 7] 21
                   [:diamond 7] 22
                   [:heart 7] 23
                   [:spade 8] 24
                   [:club 8] 25
                   [:diamond 8] 26
                   [:heart 8] 27
                   [:spade 9] 28
                   [:club 9] 29
                   [:diamond 9] 30
                   [:heart 9] 31
                   [:spade :jack] 32
                   [:club :jack] 33
                   [:diamond :jack] 34
                   [:heart :jack] 35
                   [:spade :queen] 36
                   [:club :queen] 37
                   [:diamond :queen] 38
                   [:heart :queen] 39
                   [:spade :king] 40
                   [:club :king] 41
                   [:diamond :king] 42
                   [:heart :king] 43
                   [:spade :ace] 44
                   [:club :ace] 45
                   [:diamond :ace] 46
                   [:heart :ace] 47})))

(deftest test-winner

  (testing "higher numbers of the same suit beat lower numbers"
    (is (= [:spade 3] (winner [:spade 3] [:spade 2])))
    (is (= [:spade 3] (winner [:spade 2] [:spade 3]))))
  (testing "higher numbers of lower suits beat lower numbers of higher suits"
    (is (= [:spade 3] (winner [:spade 3] [:diamond 2])))
    (is (= [:spade 3] (winner [:diamond 2] [:spade 3]))))
  (testing "higher suits beat lower suits of equal number"
    (is (= [:club :jack] (winner [:spade :jack] [:club :jack])))
    (is (= [:club :jack] (winner [:club :jack] [:spade :jack]))))

  (testing "queens are higher rank than jacks"
    (is (= [:heart :queen] (winner [:heart :queen] [:heart :jack])))
    (is (= [:heart :queen] (winner [:heart :jack] [:heart :queen]))))
  (testing "kings are higher rank than queens"
    (is (= [:heart :king] (winner [:heart :queen] [:heart :king])))
    (is (= [:heart :king] (winner [:heart :king] [:heart :queen]))))
  (testing "aces are higher rank than kings"
    (is (= [:heart :ace] (winner [:heart :ace] [:heart :king])))
    (is (= [:heart :ace] (winner [:heart :king] [:heart :ace]))))

  (testing "clubs are higher suit than spades"
    (is (= [:club :queen] (winner [:club :queen] [:spade :queen])))
    (is (= [:club :queen] (winner [:spade :queen] [:club :queen]))))
  (testing "diamonds are higher suit than clubs"
    (is (= [:diamond :queen] (winner [:diamond :queen] [:club :queen])))
    (is (= [:diamond :queen] (winner [:club :queen] [:diamond :queen]))))
  (testing "hearts are higher suit than diamonds"
    (is (= [:heart :queen] (winner [:heart :queen] [:diamond :queen])))
    (is (= [:heart :queen] (winner [:diamond :queen] [:heart :queen])))))

; a game with only the lowest 6 cards in the deck
(def game-steps '([([:club 4] [:spade 2] [:spade 3])
                   ([:spade 4] [:club 2] [:club 3])]
                  [([:spade 2] [:spade 3] [:club 4] [:spade 4])
                   ([:club 2] [:club 3])]
                  [([:spade 3] [:club 4] [:spade 4])
                   ([:club 3] [:club 2] [:spade 2])]
                  [([:club 4] [:spade 4])
                   ([:club 2] [:spade 2] [:club 3] [:spade 3])]
                  [([:spade 4] [:club 4] [:club 2])
                   ([:spade 2] [:club 3] [:spade 3])]
                  [([:club 4] [:club 2] [:spade 4] [:spade 2])
                   ([:club 3] [:spade 3])]
                  [([:club 2] [:spade 4] [:spade 2] [:club 4] [:club 3])
                   ([:spade 3])]
                  [([:spade 4] [:spade 2] [:club 4] [:club 3])
                   ([:spade 3] [:club 2])]
                  [([:spade 2] [:club 4] [:club 3] [:spade 4] [:spade 3])
                   ([:club 2])]
                  [([:club 4] [:club 3] [:spade 4] [:spade 3])
                   ([:club 2] [:spade 2])]
                  [([:club 3] [:spade 4] [:spade 3] [:club 4] [:club 2])
                   ([:spade 2])]
                  [([:spade 4] [:spade 3] [:club 4] [:club 2] [:club 3] [:spade 2])
                   ()]))

(deftest test-play-round
  (testing "game state transitions"
    (is (= (nth game-steps 1) (apply play-round (nth game-steps 0))))
    (is (= (nth game-steps 2) (apply play-round (nth game-steps 1))))
    (is (= (nth game-steps 3) (apply play-round (nth game-steps 2))))
    (is (= (nth game-steps 4) (apply play-round (nth game-steps 3))))
    (is (= (nth game-steps 5) (apply play-round (nth game-steps 4))))
    (is (= (nth game-steps 6) (apply play-round (nth game-steps 5))))
    (is (= (nth game-steps 7) (apply play-round (nth game-steps 6))))
    (is (= (nth game-steps 8) (apply play-round (nth game-steps 7))))
    (is (= (nth game-steps 9) (apply play-round (nth game-steps 8))))
    (is (= (nth game-steps 10) (apply play-round (nth game-steps 9))))
    (is (= (nth game-steps 11) (apply play-round (nth game-steps 10))))))

(def full-game [[[:club :ace]
                 [:club :jack]
                 [:spade 8]
                 [:diamond 3]
                 [:diamond :queen]
                 [:spade :ace]
                 [:club 2]
                 [:club 7]
                 [:heart 7]
                 [:spade 4]
                 [:heart 3]
                 [:heart 9]
                 [:heart :queen]
                 [:spade 7]
                 [:diamond :jack]
                 [:diamond :ace]
                 [:diamond 8]
                 [:club 4]
                 [:club 9]
                 [:diamond 9]
                 [:club 3]
                 [:heart 8]
                 [:heart :king]
                 [:spade 2]]
                [[:club :king]
                 [:spade :king]
                 [:diamond 5]
                 [:spade 9]
                 [:spade 6]
                 [:club 5]
                 [:club :queen]
                 [:heart 6]
                 [:diamond 6]
                 [:heart :jack]
                 [:spade 3]
                 [:diamond :king]
                 [:heart 4]
                 [:heart 5]
                 [:diamond 2]
                 [:spade :queen]
                 [:spade :jack]
                 [:club 6]
                 [:diamond 4]
                 [:diamond 7]
                 [:heart 2]
                 [:heart :ace]
                 [:club 8]
                 [:spade 5]]])

(deftest test-play-game
  (testing "a bunch of random games (lol)"
    (is (seq (apply play-game (deal cards))))
    (is (seq (apply play-game (deal cards))))
    (is (seq (apply play-game (deal cards))))
    (is (seq (apply play-game (deal cards)))))
  (testing "the player loses when they run out of cards"
    (is (= "Player 2 wins" (apply play-game full-game)))))
