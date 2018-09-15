(ns graphql-clj-starter.graphql
  (:require [graphql-clj.resolver :as resolver]
            [graphql-clj.executor :as executor]
            [graphql-clj.query-validator :as qv]
            [graphql-clj.schema-validator :as sv]
            [clojure.core.match :as match]
            [clj-http.client :as client]
            ))

;; graphql schema defined
(def starter-schema "

type Query {
  users: [User!]!
  user(id: Int!): User
  posts: [Post!]!
  post(id: Int!): Post
}

type User {
  id: Int!
  name: String!
  username: String!
  email: String!
  address: Address!
  phone: String!
  website: String!
  company: Company!
}

type Address {
  street: String!
  suite: String!
  city: String!
  zipcode: String!
  geo: GEO!
}

type Company {
  name: String!
  catchPhrase: String!
  bs: String!
}

type GEO {
  lat: String!
  lng: String!
}

type Post {
  id: Int!
  userId: Int!
  title: String!
  body: String!
}

schema {
  query: Query
}")

(def base_url "https://jsonplaceholder.typicode.com")

;; common http get function
(defn get-from-server [url]
  (get (client/get url {:as :json}) :body))

;; get functions
(defn get-users [] (get-from-server (str base_url "/users")))
(defn get-user [id] (get-from-server (str base_url "/users/" id)))
(defn get-posts [] (get-from-server (str base_url "/posts")))
(defn get-post [id] (get-from-server (str base_url "/posts/" id)))

;; root resolver
(defn starter-resolver-fn [type-name field-name]
  (match/match
   [type-name field-name]
   ["Query" "users"] (fn [context parent args] (get-users))
   ["Query" "user"] (fn [context parent args] (get-user (get args "id")))
   ["Query" "posts"] (fn [context parent args] (get-posts))
   ["Query" "post"] (fn [context parent args] (get-post (get args "id")))
   :else nil))

(def validated-schema (sv/validate-schema starter-schema))

(defn execute
  [query variables operation-name]
  (executor/execute nil validated-schema starter-resolver-fn query variables operation-name))

