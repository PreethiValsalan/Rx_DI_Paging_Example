# MVVMEx
<B>Loading continuously with MVVM LiveData architecture using Retrofit, RxJava & Dagger<B>

<p><strong>Retrofit</strong> is a REST client that uses OkHttp as the HttpClient and JSON Parsers to parse the response.&nbsp;<strong>RxJava</strong> is a library that is used for asynchronous and reactive programming in the form of streams. In this application&nbsp;<strong>Retrofit and&nbsp;</strong>&nbsp;<strong>RxJava&nbsp;</strong>is used to load data into the application avoiding UI lag. In order to create a clean architecture I have used <strong>MVVM</strong> architecture along with <strong>Dagger, Retofit and RxJava</strong>. Infinite scrolling is implemented using the amazing paging library from android.</p>

<ul>
  <li>LiveData</li>
  <li>ViewModel</li>
  <li>Retrofit</li>
  <li>Dagger</li>
  <li>RxJava</li>
  <li>Paging</li>
</ul>

In AndroidStudio, create a new project with EmptyActivity template named is RxPagingEx. 
Now just add extra dependencies into the app/build.gradle .

   implementation 'androidx.appcompat:appcompat:1.0.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    implementation 'com.google.android.material:material:1.0.0'

    implementation 'androidx.lifecycle:lifecycle-extensions:2.0.0'
    implementation 'androidx.paging:paging-runtime:2.1.0'

    implementation 'com.squareup.retrofit2:retrofit:2.3.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.9.1'
    implementation 'com.squareup.picasso:picasso:2.5.2'

    implementation 'io.reactivex.rxjava2:rxjava:2.2.2'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'

    implementation 'com.google.dagger:dagger:2.20'
    annotationProcessor 'com.google.dagger:dagger-compiler:2.20'
    implementation 'com.google.code.findbugs:jsr305:3.0.2'

    
