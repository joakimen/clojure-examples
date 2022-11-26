#!/usr/bin/env bb

(comment
;; no need for headers? slurp works
  (-> (slurp "https://reqres.in/api/users/2")
      (json/parse-string true)
      :data :email) ;; => "janet.weaver@reqres.in"

;; about basic auth?
  (-> "https://postman-echo.com/basic-auth"
      (curl/get {:basic-auth ["postman" "password"]})
      :body
      (json/parse-string true)) ;; => {:authenticated true}

;; logging in and getting token
  (-> "https://reqres.in/api/login"
    ;; (curl/post {:body (json/generate-string {:email "eve.holt@reqres.in" :password "cityslicka"})})
      (curl/post {:body (json/generate-string {:email "eve.holt@reqres.in" :password "cityslicka"})
                  :headers {"Content-Type" "application/json"}})
      :body
      (json/parse-string true)
      :token) ;; => "QpwL5tke4Pnpja7X4"

;; download file
  (-> (curl/get "https://github.com/babashka/babashka/raw/master/logo/icon.png"
                {:as :bytes})
      :body
      (io/copy (io/file "icon.png"))))
