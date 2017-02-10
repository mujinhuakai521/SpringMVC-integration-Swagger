(define (square x) (* x x))
(define (cube x) (* x x x))

(define (cubic a b c)
        (lambda (x) (+ (cube x) (* a (square x)) (* b x) c))
)

(load "C:/sicp/demo_1_3_4.scm")

(newtons-method (cubic 3 2 1) 1.0)
