(def start "did")
(def end "rad")

(def rdr (clojure.java.io/reader "/usr/share/dict/words"))
(def words (line-seq rdr))
(. rdr close)

(defn mitch [a b]
  (and 
   (= 1 
      (count 
      	(filter false? (map = a b)))) 
   (= (count a) (count b))))

(defn nate [carl waldo]
  (mapcat 
   #(filter 
     (partial mitch %)
     waldo)
   carl))

(defn finished [set]
  (or 
  	(some (partial = end) set)
    (empty? set)))

(first (filter
        finished
        (take 5 
              (iterate #(nate % words) 
                       [start]))))
          