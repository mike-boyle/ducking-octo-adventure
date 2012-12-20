(def start "did")
(def end "rad")

(def words ["dad" "rat" "rad"])

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
  	(contains? end set)
    (empty? set)))
          
(mitch "rad" "dad")
           
(nate ["bad" "pot"] ["fad" "dad" "bot" "oob"])

(take 5 
      (iterate #(nate [%1] words) 
               start))
          
