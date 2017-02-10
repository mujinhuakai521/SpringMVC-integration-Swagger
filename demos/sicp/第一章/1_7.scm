(define (abs x)
	(if (< x 0) (- x) x)
)

(define (square x)
	(* x x)
)

(define (average x y)
	(/ (+ x y) 2)
)

(define (improve guess x)
	(average guess (/ x guess))
)

(define (good-enough? last-guess guess x)
	(< (/ (abs (- guess last-guess)) guess) 0.001)
)

(define (sqrt-iter last-guess guess x)
	(if (good-enough? last-guess guess x)
	    guess
	    (sqrt-iter guess (improve guess x) x)
        )
)

(define (sqrt x) (sqrt-iter 0 1.0 x))
