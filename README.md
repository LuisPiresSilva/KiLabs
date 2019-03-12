Report


###Architecture:

project follows the model view presenter
 - presenters in this case are viewmodels (Android ViewModel component), so that the presenter is retained across 
 configuration changes (using the view model component makes it easier)
 - presenter communicates with datasource, holds data and states
 - each screen package has contracts interfaces that contain both the view and presenter interfaces that each should
 implement
 
 

###Navigation:
 - for this case, i simply followed a fragment navigation approach (ie i did not create a detail activity and such)
 would need more specific details about requirements or future requirements to decide if i would do an
  activity base approach (for instance if detail would need to also open from outside the app and how and why)



###Structure:
regarding project structure the following packages organise the project like so:
 - components -> i place generic components here
 - di -> overall generic dependency injection - it contains di of the common used components in this case it provides
  viewmodels that communicate with the data source layer (in this case network but would also contain local database)
  it also provides just the network layer
 - helpers -> contains Utils classes, and also different files that provide function extensions for kotlin usage
 - model -> contains classes that can be both used by local database and network
 (ie contains the entities used in the app)
 - network -> this package contains everything related to specific network (ie schema definitions, 
 contracts interfaces, served API, etc)
 note: -> FlickrRemoteDataSource -> is here because it only communicates with server but, in a synchronized 
 data approach i would rename to
 FlickrDataSource and this class would handle both network and database calls and thus it would be in a data package.
 - ui -> contains the views and i also put the presenters here since they have a strong bond with 
 the views (regarding tasks, usually we touch both), i separate screens by packages and only very generic views with a
 low chance of becoming specific to a view type go to another generic package usually inside base
 (open to discussion point, i get point of gather the presenters into a single package)
 also contains the launcher
 
 - i create a debug build type (debug folder) to hold an activity for the solely purpose of testing fragments
  in isolation (hopefully this will change when the latest, still not stable, testing libs come out)

 - androidTest and test -> are normal android folders
 - inside test folder -> base contains setup and configuration classes to be used by Test classes to fast setup


###TESTING:
 - i provide some basic Instrumentation test:
  - activity -> tests that ir reuses the same fragments
  - main fragment -> also does some tests but only on top of the kittens row the others should follow the same behaviour
  
 - i provide some unit tests and show some different ways to do testing:
  - i would prefer to use MockWebServer in a general purpose but i only show how would i use it to test the schema
  and how the functions would return the correct data for different server responses (needed to create several json
  with different server responses, success and error)
  - also have a MainViewModelTest that tests the presenter logic and make sures it calls the correct view functions for
  different scenarios and use cases






###DEVELOPER NOTES about exercise:
Given the exercise requirements and information many things were unclear therefore some decisions were made with that 
in mind and here i explained those (i know this is an exercise and that's why i simply went for it but in a real
 environment i would try to not start development until i get the following issues clearer)

####Critics/Opinions regarding exercise:
 - the exercise is not clear about future developments which makes it hard to make decisions about core structure
 (example: deeplinks, instant app and similar)
 - very generic wireframes -> main screen wireframe shows how it should look the problem is that we do not know what is
 kittens or dogs, seems like it is mixing very different things where it should not
 - detail screen does not have into account image scaling problems -> image should always be like that? is it square 
 or not?
 - and obviously wireframe are usually much more complete (ratios, sizes, colors etc) but its an exercise


####Specific Development Decisions/Approaches based on the latter critics/opinions:
 - given the doubts i added each list row dynamically (i called it categories, therefore it is category based):
  - kittens and dogs -> use a search flickr endpoint with those search terms
  - public feed -> uses getRecent flickr endpoint
 besides being added dynamically they depend on fixed constants in the client
 overall this is not a good approach unless the categories also come from the server (public feed seems fine 
 (to some degree) but kitten and dogs would potentially create problems namely if/when we start to add 
 localization support (translation of texts to other languages))
 - in detail i started with centerCrop and changed later to fit center (its a detail should should entire image), 
 again wirdeframe does not have into consideration any scaling problems and any rotation behaviour or layouts for
 landscape





###Requiremnts

####Mandatory Requirements
 - Use the latest development tools for the platform you are working on -> check
 - You may use third party libraries -> check
 - Use a software version control system -> check
 - Fetch data from the web -> check
 - Metadata must be visible for each image -> check (there is a metadata button in the detail screen)
 
####Optional Requirements
 - Image Caching -> check (glide caches images, used the default strategy -> this can be tweeked)
 - Share image -> check
 - Open image in browser -> check (button GO)
 - Save Image -> check
 - Order image by date -> missing (easy to add later)
 - Unit Tests -> check (some unit testing is done not 100% coverage (focused mainly in main fragment did not test photoDetail))
 
 
 
###Current apps features and inner workings:
 - app survives rotation and keeps states (scroll position, data and others)
 - app has swipe to refresh (requests all categories again) -> everything starts over but list retains view states
  it only changes the views if new content is different (removes or updates old cards inserts new ones) therefore
  it is normal that some swipe to refresh action seems to not do nothing or very little
 - app targets android 9, therefore for security reasons only 'api.flickr.com' for api and any '*.staticflickr.com' 
 for images domains are allowed (dont know if flickr uses more or not)
 - photo metadata is shown in a raw way (detail screen through METADATA button)
 - go opens browser
 - share and save image -> app has a private cache folder (managed by the system) that uses to store images 
 for sharing (provides access to third party apps through a file provider) and save image either downloads or copies
 image from this cache folder to public image system folder and notifies third party apps (through system)
 - app caches and uses both medium and original quality flickr photos -> while this might not be easily visible to the eye
 in detail screen app reuses already fetched medium size image (if original not present), downloads original 
 and switches medium by original when original is ready (the quality improvement might not be easily visible but it is
 there and the change might also not be noticeable but it happens)

 
 
###Features i did not implemented:

 - i did not implemented the load more for the lists (easy)
 - still did not implemented order image by date (also easy)
 - did not handle error codes (left it clear where it should be placed in the 'FlickrRemoteDataSource' class and 
 the specific to Flickr error codes and details are commented in the FlickrAPI interface
    
and obviously might have missed this and that here and there, somewhat a big exercise
 
 