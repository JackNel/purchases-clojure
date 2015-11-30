(ns purchases-clojure.core
  (:require [clojure.string :as str]
            [clojure.walk :as walk]
            [clojure.pprint :as pp]
            [ring.adapter.jetty :as j]
            [hiccup.core :as h])

  (:gen-class))

(defn read-purchases []
  ;(println "Enter a category: Furniture, Alcohol, Toiletries, Shoes, Food, or Jewelry.")
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
        purchases (walk/keywordize-keys purchases)
        ; input (read-line)
        #_purchases #_(filter (fn [line]
                            (= input (:category line)))
                          purchases)]
    #_(spit (format "filtered_%s.edn" input)
            (with-out-str (pp/pprint purchases)))
    purchases))

#_(defn purchase-item [purchase-map]
  [:p
   [:b (:category purchase-map)]
   " "
   [:i (:credit_card purchase-map)]])

(defn purchases-html []
  (let [purchases (read-purchases)]
    (map (fn [line]
           [:p
            "Customer ID: "
            (:customer_id line) [:br]
            " Date: "
            (:date line) [:br]
            " Credit Card: "
            (:credit_card line) [:br]
            " CVV: "
            (:cvv line) [:br]
            " Category: "
            (:category line) [:br]])
    purchases)))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text.html"}
   :body (h/html [:html
                  [:body
                   (purchases-html)]])})

(defn -main [& args]
  (j/run-jetty #'handler {:port 3000 :join? false}))