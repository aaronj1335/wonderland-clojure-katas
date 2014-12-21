(ns card-game-war.game)

(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 :jack :queen :king :ace])
(def cards
  (for [rank ranks
        suit suits]
    [suit rank]))
(def ordering (into {} (map-indexed #(-> [%2 %1]) cards)))

(defn deal
  "shuffle and deal a sequence of cards among players (default 2)

  try to mimic the order in which people actually deal, such that every player
  gets a card on each iteration.

  if the numbe of cards is not easily divisible by the number of players, just
  leave out the extra cards."
  ([cards]
   (deal cards 2))
  ([cards num-players]
   (apply map vector (partition num-players (shuffle cards)))))

(defn winner
  "return the winning card based on `ordering`

  works for a variable number of cards"
  [& cards]
  (->> cards
       (map #(-> {:order (ordering %) :card %}))
       (apply max-key :order)
       (:card)))

(defn play-round
  "given each players deck, play a card from each and add them to the bottom of
  the winner's deck"
  [player1-cards player2-cards]
  (let [card1 (first player1-cards)
        card2 (first player2-cards)]
    (if (= card1 (winner card1 card2))
      [(concat (rest player1-cards) [card1 card2])
       (rest player2-cards)]
      [(rest player1-cards)
       (concat (rest player2-cards) [card2 card1])])))

(defn play-game [player1-cards player2-cards]
  (loop [[player1-cards player2-cards] (play-round player1-cards player2-cards)]
    (cond
      (nil? (seq player1-cards)) "Player 2 wins"
      (nil? (seq player2-cards)) "Player 1 wins"
      :else (recur (play-round player1-cards player2-cards)))))
