(define (compose f g)
        (lambda (x) (f (g x)))
)

(define (inc x) (+ x 1))
(define (square x) (* x x))

; (x+1)**2
((compose square inc) 6)
