(ns gilded-rose.core-spec
  (:require [clojure.test :refer :all]
            [gilded-rose.core :refer [update-attributes
                                      item
                                      update-current-inventory]]
            [gilded-rose.util :as util]))

(def ^:private fixture
  [{:name "foo", :sell-in 0, :quality 0}])

(def ^:private fixture'
  '({:name "+5 Dexterity Vest", :sell-in 9, :quality 19}
    {:name "Aged Brie", :sell-in 1, :quality 1}
    {:name "Elixir of the Mongoose", :sell-in 4, :quality 6}
    {:name "Sulfuras, Hand Of Ragnaros", :sell-in -1, :quality 80}
    {:name "Backstage passes to a TAFKAL80ETC concert", :sell-in 14, :quality 21}))

(deftest gilded-rose-test
  (is (= (-> fixture
             first
             :name)
         (->> [(item "foo" 0 0)]
              update-attributes
              first
              :name))))

(deftest gilded-rose-test'
  (is (= fixture'
         (update-current-inventory))))
