DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS accounts;

CREATE TABLE "accounts" (
  "id" SERIAL PRIMARY KEY,
  "account_code" varchar,
  "business_name" VARCHAR,
  "first_name" VARCHAR NOT NULL,
  "last_name" VARCHAR NOT NULL,
  "type_id" INT NOT NULL,
  "physical_address" VARCHAR,
  "registration_number" VARCHAR,
  "email_address" VARCHAR UNIQUE NOT NULL,
  "tax_number" VARCHAR,
  "contact_number" VARCHAR UNIQUE NOT NULL,
  "alternate_contact_number" VARCHAR,
  "bank_account_number" VARCHAR,
  "debit_day" INT NOT NULL,
  "credit_card_number" VARCHAR,
  "credit_card_cvv" VARCHAR,
  "credit_card_expiry_date" TIMESTAMP(6),
  "create_date" TIMESTAMP(6) NOT NULL,
  "last_update_date" TIMESTAMP(6) NOT NULL,
  "status_id" int NOT NULL
);

CREATE TABLE "users" (
  "id" SERIAL PRIMARY KEY,
  "account_id" int NOT NULL,
  "type_id" INT NOT NULL,
  "first_name" VARCHAR NOT NULL,
  "last_name" VARCHAR NOT NULL,
  "username" VARCHAR UNIQUE NOT NULL,
  "password" VARCHAR NOT NULL,
  "mobile_number" VARCHAR NOT NULL,
  "email_address" VARCHAR NOT NULL,
  "create_date" TIMESTAMP(6) NOT NULL,
  "last_update_date" TIMESTAMP(6) NOT NULL,
  "status_id" int NOT NULL,
  UNIQUE ("account_id", "username")
);

ALTER TABLE "users" ADD FOREIGN KEY ("account_id") REFERENCES "accounts" ("id");




