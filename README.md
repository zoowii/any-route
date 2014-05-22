# any-route

一个简单的双向路由库(路由 <=> handler + params的双射),主要可以用于Java/Clojure的Web应用的路由基础,也可以独立使用(对,又是一个轮子)

提供了基本的路由core和http封装

使用Clojure实现,暂时没有提供Java的Wrapper

BUG较多,没有做充分测试,慎用慎用

## Usage

使用:abc用来在一个分隔符里匹配,使用:*abc可以跨分隔符匹配,默认分隔符是'/',暂时不支持修改分隔符

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


    ;; 以下是http的封装的例子(多一个根据http method进行路由的功能,如果要匹配多种method,使用:ANY)
    (def http-rtbl
      (make-route-table
        [
          (http-route :GET
                      "/test/:id/update" "test_handler" "test")
          (context-route
            "/user"
            [(http-route
               :GET "/:id/view/:project/:*path" "view_user_handler" "view_user")
             (http-route
               :POST "/:id/view/:project/:*path" "update_user_handler" "update_user")])]))
    (println http-rtbl)
    (println (find-route-in-http-route-table http-rtbl "update_user"))

    (println (http-route-table-match http-rtbl :GET test-url))
    (println (reverse-in-http-route-table
               http-rtbl "update_user" "433" "test-project" "github.com/zoowii"))

## License

Copyright © 2014 zoowii

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
