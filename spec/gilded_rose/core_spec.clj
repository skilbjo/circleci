(ns gilded-rose.core-spec
  (:require [clojure.test :refer :all]
            [gilded-rose.core :refer [update-quality item]]
            [gilded-rose.util :as util]))

(def ^:private fixture
  [{:name "foo", :sell-in 0, :quality 0}])

(deftest gilded-rose-test
  (is (= (-> fixture
             first
             :name)
         (->> [(item "foo" 0 0)]
              update-quality
              first
              :name))))
