(define (gcd a b)
	(if (= b 0)
            a
            (gcd b (remainder a b))
        )
)
;正则序
;(gcd 206 40)
;(gcd 40 (remainder 206 40))  <---- a1 = (remainder 206 40)
;(if (= a1 0) 40 (gcd a1 (remainder 40 a1)))  ==> a1 三次使用 (gcd a1 (remainder 40 a1))
;....


;应用序
; (gcd 206 40)
; (gcd 40 (remainder 206 40))  ==> (gcd 40 6)
; (gcd 6 (remainder 40 6))   ==> (gcd 6 4)
; (gcd 4 (remainder 6 4))  ==> (gcd 4 2)
; (gcd 2 (remainder 4 2))  ==> (gcd 2 0)
; 2 
