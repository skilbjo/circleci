(ns gilded-rose.core
  (:refer-clojure :exclude [name])
  (:require [gilded-rose.util :as util])
  (:gen-class))

(defn set-quality! [item v]
  (merge item {:quality v}))

(defn dec-quality! [item]
  (merge item {:quality (-> item :quality dec)}))

(defn inc-quality! [item]
  (merge item {:quality (-> item :quality inc)}))

(defn update-quality!
  [{:keys [name
           sell-in
           quality] :as item}]
  (cond
    (neg? sell-in)                         ; Once the sell by date has passed,
    (condp name                          ; quality degrades twice as fast
           "+5 Dexterity Vest"                (->> item
                                                   (iterate dec-quality!)
                                                   (take (+ 1 2))
                                                   last)
           "Elixir of the Mongoose"           (->> item
                                                   (iterate dec-quality!)
                                                   (take (+ 1 2))
                                                   last)
           "Backstage passes to a TAFKAL80ETC concert"     (set-quality! item 0))

    (= name "Backstage passes to a TAFKAL80ETC concert") ; Backstage passes
    (cond                                              ; rules
      (and (>= sell-in 5) (< sell-in 10)) (->> item
                                               (iterate inc-quality!)
                                               (take (+ 1 2))
                                               last)
      (and (>= sell-in 0) (< sell-in 5))  (->> item
                                               (iterate inc-quality!)
                                               (take (+ 1 3))
                                               last)
      :else (when (< quality 50)
              (inc-quality! item)))

    (or (= "+5 Dexterity Vest" name)       ; At the end of each day our system
        (= "Elixir of the Mongoose" name)) ; lowers both values for every item
    (dec-quality! item) (= name "Aged Brie")                   ; "Aged Brie" actually increases
    (when (< quality 50)                 ; in quality the older it gets
      (inc-quality! item))

    (= name "Conjured")                   ; "Conjured" items degrade in quality
    (->> item
         (iterate dec-quality!)
         (take (+ 1 2))
         last)

    :else item))

(defn dec-sell-in-days
  "Decrease the :sell-in value, except for when item is a legendary 'Sulfuras'"
  [{:keys [name
           sell-in] :as item}]
  (when (not= "Sulfuras, Hand of Ragnaros" name)
    (merge item {:sell-in (-> sell-in dec)})))

(defn update-attributes [items]
  (->> items
       (map update-quality!)
       (map dec-sell-in-days)))

(defn item [item-name, sell-in, quality]
  {:name item-name, :sell-in sell-in, :quality quality})

(defn update-current-inventory []
  (let [inventory
        [(item "+5 Dexterity Vest" 10 20)
         (item "Aged Brie" 2 0)
         (item "Elixir of the Mongoose" 5 7)
         (item "Sulfuras, Hand Of Ragnaros" 0 80)
         (item "Backstage passes to a TAFKAL80ETC concert" 15 20)
         (item "Conjured" 15 20)]]
    (update-attributes inventory)))

(defn -main []
  (println (update-current-inventory)))
