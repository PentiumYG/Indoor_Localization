# Assignment 5: Indoor Localization using WIFi
● Implementation of indoor localization app that will provide coarse-grained accuracy.

● Used pedestrian dead reckoning (PDR) to compute displacement from accelerometer
reading

    ○ For this, compute your stride length (Hint: there are some heuristics to calculate 
    stride length based on your height and weight)
    
    ○ Compute the number of steps taken (use accelerometer pattern)
    
    ○ Determine the direction of moving (using a magnetometer)
#### Note that here you assume that the user holds the phone in hand and y-axis is towards north or to your front. You also assume that the user does not shake his phone, you know the GPS of the entrance point from where the user started walking.
● Showcase the steps you have taken and the direction of your walk. Design a nice UI to
showcase this information (see the sample ignore the calory, km, or time part). This is
just a sample. Design your UI as you seem appropriate.

● Reset your error in PDR using anchor points that you identify using WiFi RSSI.

● Get the WiFi scan results to know the list of access points nearby and their RSSI values\
(Hint: https://developer.android.com/reference/android/net/wifi/WifiManager
https://developer.android.com/reference/android/net/wifi/WifiInfo#getRssi()
https://developer.android.com/reference/android/net/wifi/ScanResult
).

● Wardrive inside your home/R&D building/hostel/seminar block/old academic building of
IIITD and get RSSI measurements of the APs from different regions or rooms of your
home/respective buildings using WiFi scan results. If you are on the campus, choose a
building and a floor on that building that has at least 10 rooms. Collect data for these 10
rooms. If you are at home find 10 diff places at your home. Annotate WiFi RSSI values
with the true location. Store this information.

● Given a test scenario, where a new user walks in determine the location of the user
separately using PDR and RSSI-based wardriving. Note that for RSSI matching, it will
match it to stored information with a single point that is most similar to the test data.
Design an appropriate UI to show the location.

    ● Optional1: implementing the matching with KNN

    ● Optional2: Identify anchor points in an indoor environment using sensor fingerprints. You 
    can manually tag anchor points’ true location. Take 2-3 sensors for identifying sensor fingerprints.
    
### Rubric:
1. Count number of steps: 2 marks, determine direction: 2 marks, stride length: 1 mark
2. Showcasing this using appropriate UI: 3 marks
3. Collect RSSI based training data and save: 5 marks
4. Testing of localization app: UI + returning the correct location: 5 marks
5. Bonus marks optional 1: 3 marks
6. Bonus marks optional 2: 3 marks
