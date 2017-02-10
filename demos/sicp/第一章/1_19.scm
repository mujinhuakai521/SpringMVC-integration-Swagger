;T(p, q):
;a <- bq + aq + ap
;b <- bp + aq

;T(p1, q1) :
;a <- bq + a(p+q)
;b <- bp + aq

;a <- (bp + aq)q + (bq + a(p+q))(p+q) 
;      = bpq + aqq + bpq + bqq + a(p+q)(p+q)
;      = 2bpq + aqq + bqq + app + aqq + 2apq
;      = 2bpq + 2aqq + bqq + app + 2apq
;      = b(2pq + qq) + a(2qq+pp+2pq)

;b <- (bp+aq)p + (bq+aq+ap)q
;    = bpp + aqp + bqq + aqq + apq
;    = b(pp+qq) + a(2pq + qq)

;p1 = pp + qq
;q1 = 2pq + qq

(define (fib n)
	(define (fib-iter a b p q count)
                (cond ((= count 0) b)
                      ((even? count) (fib-iter a 
					       b
                                               (+ (* p p) (* q q))
                                               (+ (* 2 p q) (* q q)) 
                                               (/ count 2)))
                      (else (fib-iter (+ (* b q) (* a q) (* a p))
                                      (+ (* b p) (* a q))
                                      p
                                      q
                                      (- count 1)
                             )
                       )
                  )
         )
         (fib-iter 1 0 0 1 n)
)

