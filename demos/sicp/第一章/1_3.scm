(define (sum-of-max x y z) 
        (cond ((and (not (> x y)) (not (> x z))) (+ y z))
	       ((and (not (> y x)) (not (> y z))) (+ x z))
               (else (+ x y))
))

