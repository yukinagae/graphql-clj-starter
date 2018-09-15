(ns graphql-clj-starter.schema)

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
