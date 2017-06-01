For current task MVP pattern was chosen and used.

In nutshell we have three main components:

1. Data layer - in represented by model package and responsible for loading data and building data models.
    1) VideoBuilder - is for inner use with Gson library
    2) Video - is immutable and designed to be used outside
    3) VideoInterface - is interface required by retrofit
    4) VideoDeserializer - is responsible for deserialize Json data from server into data model
    4) VideosApi - is class to be used to the clients and responsible for loading data

2. Presentation Layer - is responsible for loading data from server, adopting and caching it and handling events from view.
    1) VideosPresenter - has Subscription for the VideosApi results and BehaviorSubject for returning results to views that is binding to it.

3. View Layer - is responsible for showing data to user and collection input events from user
    1) VideosView - interface which should be implemented by the class which will interact with presenter
    2) VideosActivity - implements VideosView and here is the main view component
