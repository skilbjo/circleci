(ns gilded-rose.core
  (:require [gilded-rose.util :as util])
  (:gen-class))

(defn set-quality=0! [item]
  (merge item {:quality 0}))

(defn dec-quality! [item]
  (merge item {:quality (-> item :quality dec)}))

(defn inc-quality! [item]
  (merge item {:quality (-> item :quality inc)}))

(defn update-quality! [item]
  (cond
    (and (neg? (:sell-in item)) (= "Backstage passes to a TAFKAL80ETC concert" (:name item)))
      (set-quality=0! item)
    (neg? (:sell-in item))
      (if (or (= "+5 Dexterity Vest" (:name item)) (= "Elixir of the Mongoose" (:name item)))
        (merge item {:quality (- (:quality item) 2)})
        item)
    (or (= (:name item) "Aged Brie") (= (:name item) "Backstage passes to a TAFKAL80ETC concert"))
      (if (and (= (:name item) "Backstage passes to a TAFKAL80ETC concert") (>= (:sell-in item) 5) (< (:sell-in item) 10))
        (merge item {:quality (inc (inc (:quality item)))})
        (if (and (= (:name item) "Backstage passes to a TAFKAL80ETC concert") (>= (:sell-in item) 0) (< (:sell-in item) 5))
          (merge item {:quality (inc (inc (inc (:quality item))))})
          (when (< (:quality item) 50)
            (inc-quality! item))))
    (or (= "+5 Dexterity Vest" (:name item)) (= "Elixir of the Mongoose" (:name item)))
      (dec-quality! item)
    :else item))

(defn dec-sell-in-days
  "Decrease the :sell-in value, except for when item is a legendary 'Sulfuras'"
  [item]
  (when (not= "Sulfuras, Hand of Ragnaros" (:name item))
    (merge item {:sell-in (-> item :sell-in dec)})))

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
         (item "Backstage passes to a TAFKAL80ETC concert" 15 20)]]
    (update-attributes inventory)))

(defn -main []
  (println (update-current-inventory)))
