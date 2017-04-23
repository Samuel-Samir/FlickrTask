# FlickrTask

## App architecture
![capture1](https://cloud.githubusercontent.com/assets/20879706/25318165/6919a10a-2889-11e7-9588-7ba775dd4680.PNG)

### cache package contain three class to handle download photos and cache it
  (FileCache - MemoryCache -ImageLoader)
  
### model package contain three class  for holding data

### remote package contain two class for get flickr api response

### Services package contain  on class  
  MyService extends from JobService to update automatically every 1 min
  
### UI package
  #### Activity 
     Main activity
     Splash Screen
  #### Adapter to handle to handle recyclerview functions
  #### fragment 
     FlickerFragment to display a grid of images
     DisplayPhotoFragment to display selected photo
  #### OnSwipeTouchListener to handle swipe left or right to display next image
  
  ## Contains menu refreshe to clear cache and get new response
  
  ![device1](https://cloud.githubusercontent.com/assets/20879706/25318338/8e87d95e-288c-11e7-8ec5-df0e15ad36cf.png)


  ![device-3png](https://cloud.githubusercontent.com/assets/20879706/25318351/26bc91d8-288d-11e7-87ec-7508d0497c31.png)

  

