
# MobileSensing
An android application that is built to assist visually impaired persons, mainly the elderly in detecting objects and receiving visual (bounding boxes) and auditory feedback.

## Requirements
- Android Studio (Tested on Bumblebee 2021.1.1 Patch 1)
- Android Device with Camera
- (Optional) Android Device with TTS Support

## Datasets
### Combined Dataset (22,500 Images, 12 Classes)
- [Common Objects  Dataset (sythesis.ai)](https://synthesis.ai/2020/08/13/object-detection-with-synthetic-data-ii-common-objects-and-their-context/)
- [Falling Objects Dataset (research.nvidia.com)](https://research.nvidia.com/publication/2018-06_Falling-Things)
- [Household Dataset (vision.caltech)](http://www.vision.caltech.edu/pmoreels/Datasets/Home_Objects_06/#Description)
- [Household Dataset (robofeihome-team)](https://ieee-dataport.org/open-access/annotated-image-dataset-household-objects-robofeihome-team)
- [Household Dataset (washington.edu)](https://www.cs.washington.edu/research/rgb-d-object-dataset)
- [Nursing Home Dataset (Asmida Ismail)](https://data.mendeley.com/datasets/fpctx3svzd/1)
- [Jewelry Dataset (princesegzy01)](https://github.com/princesegzy01/Jewellery-Classification)
### Open Images Dataset V6 (36,614 Images, 8 Classes)
- [Open Images Dataset V6](https://storage.googleapis.com/openimages/web/index.html)
    - [Download Dataset Parts Script](Scripts/run_download_dataset.py)
    - [Create Annotated CSV Script](Scripts/run_create_csv.py)

## References:
- [Losing Items in the Psychogeriatric Nursing Home](https://www.ncbi.nlm.nih.gov/pmc/articles/PMC5486488/)
- [World Health Organisation, Blindness and Impairment Study](https://www.who.int/news-room/fact-sheets/detail/blindness-and-visual-impairment#:~:text=1%20Globally%2C%20at%20least%202.2%20billion%20people%20have)
- [Fiftyone Python Library](https://github.com/voxel51/fiftyone)
