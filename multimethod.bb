#!/usr/bin/env bb

(comment

  (defmulti talk (fn [animal] [(:species animal)]))
  (defmethod talk [:dog] [dog] "woof")
  (defmethod talk [:cat] [cat] "meow")
  (talk {:species :dog}) ;; => "woof" 
  (talk {:species :cat}) ;; => "meow"

  (defmulti download (fn [file] [(:protocol file)]))
  (defmethod download [:https] [file] (-> (curl/get (:url file)) :body (json/parse-string true)))
  (defmethod download [:sftp] [file] "downloading file over sftp..")

  (-> (download {:protocol :https
                 :url "https://reqres.in/api/users/1"})
      :data
      (select-keys [:id :email]))
  ;; => {:id 1, :email "george.bluth@reqres.in"}

  (download {:protocol :sftp
             :hostname "localhost"
             :auth {:username "kevin-bacon"
                    :private-key "/home/kevin/.ssh/id_rsa"}
             :filepath "/etc/shadow"})
  ;; => "downloading file over sftp.."
  )
