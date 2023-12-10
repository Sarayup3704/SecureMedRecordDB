import random
import names
import csv

# Generate random data for the given fields
def generate_random_data():
    first_name = names.get_first_name()
    last_name = names.get_last_name()
    gender = random.choice(["Male", "Female"])
    age = random.randint(18, 80)
    weight = round(random.uniform(50, 100), 2)
    height = round(random.uniform(150, 200), 2)
    health_history = "HealthHistory" + str(random.randint(1, 100))

    return (first_name, last_name, gender, age, weight, height, health_history)

# Generate data for 100 rows
data_rows = [generate_random_data() for _ in range(100)]

# Specify the CSV file path
csv_file_path = "healthcare_data.csv"

# Write data to CSV file
with open(csv_file_path, mode='w', newline='') as csv_file:
    fieldnames = ['first_name', 'last_name', 'gender', 'age', 'weight', 'height', 'health_history']
    writer = csv.DictWriter(csv_file, fieldnames=fieldnames)

    # Write CSV header
    writer.writeheader()

    # Write data rows to CSV file
    for data in data_rows:
        writer.writerow(dict(zip(fieldnames, data)))

print(f"Data has been written to {csv_file_path}.")
