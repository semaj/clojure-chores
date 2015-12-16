(ns clojure-chores.core
  (:use [clojure.java.io])
  (:require [clojure.string :as str])
  (:gen-class))

(def FILE (System/getenv "CHORES_FILE"))

(defn now [] (java.util.Date.))

(defn rotate [chores]
  (conj (vec (rest chores)) (first chores)))

(defn -main
  "Rotates chores for the week."
  [& Args]
  (let [raw (slurp FILE)
        split_up (doall (str/split raw #"\n"))
        date (first split_up)
        people (second split_up)
        chores (nth split_up 2)
        split_people (str/split people #" ")
        split_chores (str/split chores #" ")
        ]
    (cond (= "rotate" (first Args)) (spit FILE (str/join "\n" (list (now) people (str/join " " (rotate split_chores)))))
          (= "show" (first Args)) (println (map vector split_people split_chores))
          :else (println "Command must be either rotate or show."))))
