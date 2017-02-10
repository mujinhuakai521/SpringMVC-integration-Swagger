;总的数目 = 
;将现金 amount 换成没有第一种硬币后所有*其他*硬币的不同方式数目
; + 将现金 amount-第一种硬币币值 换成*所有*种类的硬币的不同方式数目

; 总的数目 N(n, k) = N(n, k-1) + N(n-v, k)
; N(n, 5) = N(n, 4) + N(n-50, 5) + 1
; N(n, 4) = N(n, 3) + N(n-25, 4) + 1
; N(n, 3) = N(n, 2) + N(n-10, 3) + 1
; N(n, 2) = N(n, 1) + N(n-5, 2) + 1
; N(n, 1) = N(n, 0) + N(n-1, 1) + 1
; N(N, 0) = 1

;===> N(n, 1) = 1 + 1 + N(n-1, 1) = 2 + N(n-1, 1)
;===> N(n, 1) = 2*n + N(0, 1)
;===> N(n, 1) = 2*n + 1 = Φ(n)
;===> N(n, 2) = 2*n + 1 + N(n-5, 2) + 1 = 2*(n+1) + N(n-5, 2)
;===> N(n, 2) = (n/5)*2*(n+1) + N(0, 2) = (n/5)*2*(n+1) + 1 = Φ(n**2)
;===> N(n, 3) = Φ(n**2) + N(n-10, 3) + 1 = (n/10)Φ(n**2) = Φ(n**3)
;===> N(n, 4) = Φ(n**3) + N(n-25, 4) + 1 = (n/25)*Φ(n**3) = Φ(n**4)
;===> N(n, 5) = Φ(n**4) + N(n-50, 5) + 1 = Φ(n**5)



(define (count-change amount)
	(define (first-denomination kinds-of-coins)
		(cond ((= kinds-of-coins 1) 1)
		      ((= kinds-of-coins 2) 5)
		      ((= kinds-of-coins 3) 10)
	              ((= kinds-of-coins 4) 25)
                      ((= kinds-of-coins 5) 50)
                )
         )
	(define (cc amount kinds-of-coins)
		(cond ((= amount 0) 1)
		      ((or (< amount 0) (= kinds-of-coins 0)) 0)
                       (else (+ (cc amount (- kinds-of-coins 1))
				(cc (- amount (first-denomination kinds-of-coins)) kinds-of-coins)
                             )
                        )
                )
          )
          (cc amount 5)
)
