(define (repeated f n)
        (if (= n 1)
            f
            (lambda (x) (f ((repeated f (- n 1)) x)))
        )
)

(define (square x) (* x x))

; (square (square 5))
((repeated square 2) 5)
