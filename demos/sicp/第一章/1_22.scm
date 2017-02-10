(define (smallest-divisor n)
		(find-divisor n 2)
)

(define (find-divisor n test-divisor)
		(cond ((> (square test-divisor) n) n)
			  ((divides? test-divisor n) test-divisor)
			  (else (find-divisor n (+ test-divisor 1)))
		)
)

(define (divides? a b)
		(= (remainder b a) 0)
)

(define (prime? n)
		(= n (smallest-divisor n))
)

(define (next-odd n)
		(cond ((odd? n) (+ 2 n))
			  (else (+ 1 n))
		)
)

(define (search-primes n count)
		(cond ((= count 0) (display "over."))
			  ((prime? n) (display n) (newline) (search-primes (next-odd n) (- count 1)))
			  (else (search-primes (next-odd n) count))
		)
)

(define (search-for-primes n)
		(let ((start-time (real-time-clock)))
			 (search-primes n 3)
			 (- (real-time-clock) start-time)
	    )
)
			