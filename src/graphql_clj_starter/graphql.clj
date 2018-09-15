(ns graphql-clj-starter.graphql
  (:require [graphql-clj.resolver :as resolver]
            [graphql-clj.executor :as executor]
            [graphql-clj.query-validator :as qv]
            [graphql-clj.schema-validator :as sv]
            [clojure.core.match :as match]))

;; graphql schema defined
(def starter-schema "

type User {
  id: Int!
  name: String!
  email: String!
  posts: [Post]
}

type Post {
  id: Int!
  title: String!
}

type Query {
  user(id: Int!): User!
}

schema {
  query: Query
}")

;; user dummy data
(def user1 {:id 1
            :name "user1"
            :email "1@example.com"
            :posts ["2" "3"]
            })
(def user2 {:id 2
            :name "user2"
            :email "2@example.com"
            :posts ["1"]
            })
(def user3 {:id 3
            :name "user3"
            :email "3@example.com"
            :posts []
            })

(def userData (atom {
                     "1" user1
                     "2" user2
                     "3" user3
                     }))

;; post dummy data
(def post1 {:id 1
            :title "post1"
            })
(def post2 {:id 2
            :title "post2"
            })
(def post3 {:id 3
            :title "post3"
            })

(def postData (atom {
                     "1" post1
                     "2" post2
                     "3" post3
                     }))

;; get functions
(defn get-user [id] (get @userData (str id)))
(defn get-post [id] (get @postData (str id)))
(defn get-posts [user] (map get-post (:posts user)))

;; root resolver
(defn starter-resolver-fn [type-name field-name]
  (match/match
   [type-name field-name]
   ["Query" "user"] (fn [context parent args] (get-user (get args "id")))
   ["User" "posts"] (fn [context parent args] (get-posts parent))
   :else nil))

(def validated-schema (sv/validate-schema starter-schema))

(defn execute
  [query variables operation-name]
  (executor/execute nil validated-schema starter-resolver-fn query variables operation-name))

