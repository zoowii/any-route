(ns any-route.test
  (:use any-route.core
        any-route.http))

(def test-url "/user/123/view/any-note/abc/def")
(def test-route "/user/:id/view/:project/:*path")

(println ((make-route test-route) test-url))
(println (make-un-route test-route 123 "dd" "ddfa/dd"))

;; 建立路由表
(def rtbl (make-route-table
            [(route "/test/:id/update" "test_handler" "test")
             (context-route
               "/user"
               [(route "/:id/view/:project/:*path" "view_user_handler" "view_user")])]))
(println rtbl)
(println (find-route-in-route-table rtbl "view_user"))

;; 进行路由匹配
(println (route-table-match rtbl test-url))

;; 路由反转
(println (reverse-in-route-table
           rtbl "view_user" "433" "test-project" "github.com/zoowii"))

(def user-http-routes
  [(GET "/:id/view/:project/:*path" "view_user_handler" "view_user")
   (POST "/:id/view/:project/:*path" "update_user_handler" "update_user")])

(defroutes http-rtbl
           (GET "/test/:id/update" "test_handler" "test")
           (context "/user" user-http-routes))

(println http-rtbl)
(println (find-route-in-http-route-table http-rtbl "update_user"))

(println (http-route-table-match http-rtbl :GET test-url))
(println (reverse-in-http-route-table
           http-rtbl "update_user" "433" "test-project" "github.com/zoowii"))