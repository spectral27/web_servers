# Insert
curl -s -w "\n\n" -X POST http://localhost:8080/test \
  -H "Content-Type: application/json" \
  -d '{"origin": "J"}'

# Select
curl -s http://localhost:8080/test | json_pp
echo ""

# Store first record id
# -r: Without quotes
# .[0]: First element of array
# .id: Element field
recordid=$(curl -s http://localhost:8080/test | jq -r .[0].id)

# Update
curl -s -w "\n\n" -X PUT http://localhost:8080/test/$recordid \
  -H "Content-Type: application/json" \
  -d '{"origin": "UPDATE"}'

# Select
curl -s http://localhost:8080/test | json_pp
echo ""

# Delete
curl -s -w "\n\n" -X DELETE http://localhost:8080/test/$recordid

# Select
curl -s http://localhost:8080/test | json_pp
echo ""
