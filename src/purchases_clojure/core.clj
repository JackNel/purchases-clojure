(ns purchases-clojure.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn -main [& args]
  (let [purchases (slurp "purchases.csv")
        purchases (str/split-lines purchases)
        purchases (map (fn [line]
                      (str/split line #","))
                    purchases)
        header (first purchases)
        purchases (rest purchases)
        purchases (map (fn [line]
                      (interleave header line))
                    purchases)
        purchases (map (fn [line]
                         (apply hash-map line))
                       purchases)
        ](pprint purchases)))