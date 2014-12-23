(ns doublets.solver-test
  (:require [clojure.test :refer :all]
            [doublets.solver :refer :all]))

(deftest valid-next-word-test
  (testing "valid next word"
    (is (valid-next-word "cheat" "wheat"))
    (is (valid-next-word "wheat" "cheat"))
    (is (valid-next-word "cheat" "cheap")))
  (testing "invalid next word"
    (is (not (valid-next-word "foo" "bar"))))
  (testing "invalid word because they are the same"
    (is (not (valid-next-word "jay-z" "jay-z"))))
  (testing "invalid word because they are not the same length"
    (is (not (valid-next-word "hoof" "hooves")))
    (is (not (valid-next-word "hooves" "hoof")))
    (is (not (valid-next-word "kanye" "kanye west")))
    (is (not (valid-next-word "kanye west" "kanye")))))

(deftest solver-test
  (testing "with word links found"
    (is (= ["head" "heal" "teal" "tell" "tall" "tail"]
           (doublets "head" "tail")))

    (is (= ["door" "boor" "book" "look" "lock"]
           (doublets "door" "lock")))

    (is (= ["bank" "bonk" "book" "look" "loon" "loan"]
           (doublets "bank" "loan")))

    (is (= ["wheat" "cheat" "cheap" "cheep" "creep" "creed" "breed" "bread"]
           (doublets "wheat" "bread"))))

  (testing "with no word links found"
    (is (= nil
           (doublets "ye" "freezer"))))
  )
