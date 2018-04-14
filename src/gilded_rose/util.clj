(ns gilded-rose.util
  (:require [clojure.pprint :as pprint]))

(defn print-it [x]
  (pprint/pprint x)
  x)
