(ns gilded-rose.core-spec
  (:require [clojure.test :refer :all]
            [gilded-rose.core :refer [update-attributes
                                      item
                                      update-current-inventory]]
            [gilded-rose.util :as util]))

(def ^:private fixture
  '({:name "+5 Dexterity Vest", :sell-in 9, :quality 19}
    {:name "Aged Brie", :sell-in 1, :quality 1}
    {:name "Elixir of the Mongoose", :sell-in 4, :quality 6}
    {:name "Sulfuras, Hand Of Ragnaros", :sell-in -1, :quality 80}
    {:name "Backstage passes to a TAFKAL80ETC concert", :sell-in 14, :quality 21}))

(def ^:private feature
  '({:name "Conjured" :sell-in 14 :quality 18}))

(deftest gilded-rose-test
  (testing "Is original data the same after our changes?"
    (is (= fixture
           (->> (update-current-inventory)
                (remove (fn [m] (= (-> m :name) "Conjured")))))))
  (testing "Is our feature implmented?"
    (is (= (concat fixture feature)
           (update-current-inventory)))))
