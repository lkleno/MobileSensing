import fiftyone as fo
import fiftyone.zoo as foz
import os
from fiftyone import ViewField as F

dataset = foz.load_zoo_dataset(
              "open-images-v6",
              split="train",
              label_types=["detections"],
              classes=["Watch", "Earrings", "Glasses", "Necklace", "Wheelchair", "Clothing", "Coin", "Footwear"],
              max_samples=20000,
              seed=712,
              shuffle=True
          )

dataset = dataset.filter_labels("detections", (F("label") == "Watch") |
                                              (F("label") == "Earrings") |
                                              (F("label") == "Glasses") |
                                              (F("label") == "Necklace") |
                                              (F("label") == "Wheelchair") |
                                              (F("label") == "Clothing") |
                                              (F("label") == "Coin") |
                                              (F("label") == "Footwear"))

dataset.export(
    export_dir=os.path.abspath("E:/dataset"),
    dataset_type=fo.types.COCODetectionDataset,
    label_field="detections",
)