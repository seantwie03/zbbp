Table item_types {
  type varchar(10) [pk]
}

Table categories {
  name varchar(20) [pk]
}

Table line_items {
  id bigint [pk]
  type varchar(10)
  budget_date date
  name varchar(50)
  planned_amount int
  category varchar(20)
  description varchar(255)
  last_modified_at timestamptz
  indexes {
      (name, budget_date) [unique]
  }
}

Table transactions {
  id bigint [pk]
  type varchar(10)
  date date
  merchant varchar(50)
  amount int
  line_item_id int
  description varchar
  last_modified_at timestamptz
}

Ref: line_items.type > item_types.type
Ref: transactions.type > item_types.type
Ref: line_items.category > categories.name
Ref: transactions.line_item_id > line_items.id
