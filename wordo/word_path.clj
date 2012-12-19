(def start "did")
(def end "rad")

(def words ["dad" "rat" "rad"])

(defn matcho [a b]
  (and 
   (= 1 
      (count 
      	(filter false? (map = a b)))) 
   (= (count a) (count b))))

(defn neigh [cur words]
  (mapcat (fn [word] 
            (filter 
             (fn [other]
               (matcho word other))
             words)) cur))

(defn finished [set]
  (or 
  	(contains? end set)
    (empty? set)
   ))
          
(matcho "rad" "dad")
           
(neigh ["rad" "bob"] ["fad" "dad" "bot" "oob"])

(first (filter finished
               (iterate (fn [[cur words]] 
                   (neigh [cur] words))
                   [start words])))
          
