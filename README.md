# ListingWebApp

A web application which provides reports based on uploaded listings

Steps to run the solution:

If Docker is installed on the system, just execute the build script 'build-image-and-run.sh'
If Docker isn't installed, the application can still be run using Maven from the location /WebApp using the command 'mvn spring-boot:run'

To test the solution, run the server and access the index HTML page at 'localhost:8080'. Then -->

1) Upload a listing file using the 'Upload Listing' option
2) Upload a contacts file using the 'Upload Contacts' option
3) Average Listing price --> 'View Average Listing Price Report' option
4) Percentage Distribution --> 'View Percentage Distribution Report' option
5) Top 30 most contacted listings --> 'View Most Contacted Listing Report' option
6) Monthly report --> 'View Monthly Report' option

All the above 4 reports have additionally been exposed as REST APIs:

1) Average Listing price --> /average
2) Percentage Distribution --> /distribution
3) Top 30 most contacted listings --> /top30
4) Monthly report --> /top5monthly


All the above endpoints (REST and Web) have separate unit tests

NOTE: I have performed most of the report computation using Java. This is much slower than actually writing the appropriate queries in SQL and using Java to format the appropriate data. However, I proceeded in this manner because I was having issues with the H2 in-memory database that I used for this purpose (I suspect some issues with the naming strategy).