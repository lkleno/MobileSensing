import csv
import json

# load json file
# get image names from json
# loop through image names
#   get image
#   get width and height
#   calculate new bounding box
#   csv

set = "UNASSIGNED"
bucket = "gs://mobile_sensing_dataset/"

header = ["set","path","label","x_min","y_min","x_max","y_min","x_max","y_max","x_min","y_max"]
csv_f = open('labels.csv', 'w', encoding='UTF8', newline='')
csv_writer = csv.writer(csv_f)
csv_writer.writerow(header)

labels_f = open("labels.json")
labels_json = json.load(labels_f)
categories_json = labels_json["categories"]
images_json = labels_json["images"]
annotations_json = labels_json["annotations"]


for annotation_json in annotations_json:
    image_json = images_json[annotation_json["image_id"] - 1]
    image_name = image_json["file_name"]
    image_width = image_json["width"]
    image_height = image_json["height"]

    image_path = bucket + image_name

    label_name = categories_json[annotation_json["category_id"]]["name"]

    bbox = annotation_json["bbox"]
    x_min = round(bbox[0] / image_width, 2)
    y_min = round(bbox[1] / image_height, 2)
    x_max = round((bbox[0] + bbox[2]) / image_width, 2)
    y_max = round((bbox[1] + bbox[3]) / image_height, 2)
    
    csv_writer.writerow([set, image_path, label_name, x_min, y_min, x_max, y_min, x_max, y_max, x_min, y_max])

csv_f.close()
labels_f.close()