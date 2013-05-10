(ns eungdap.request-reader-spec
  (:require [speclj.core :refer :all]
            [eungdap.request-reader :refer :all]))
;(import '[java.io InputStream])
;(describe "Request Reader"
  ;(it "properly reads multiple lines"
  ;  (binding [tester (InputStream. "abcdef")]
  ;  (should= "abc\r\ndef"
  ;    (read-request tester)))))
