
(load "C:/sicp/demo_1_3_3.scm")

(define (square x) (* x x))

(define dx 0.00001)

(define (deriv g)
        (lambda (x)
                (/ (- (g (+ x dx)) (g x)) dx)
        )
)

(define (newton-transform g)
        (lambda (x) (- x (/ (g x) ((deriv g) x))))
)


(define (newtons-method g guess)
        (fixed-point (newton-transform g) guess)
)

(define (sqrt x)
        (newtons-method (lambda (y) (- (square y) x)) 1.0)
)


(define (fixed-point-of-transform g transform guess)
        (fixed-point (transform g) guess)
)

(define (sqrt2 x)
        (fixed-point-of-transform (lambda (y) (/ x y))
                  average-damp
                  1.0
        )
)

(define (sqrt3 x)
        (fixed-point-of-transform (lambda (y) (- (square y) x))
                  newton-transform
                  1.0
        )
)
