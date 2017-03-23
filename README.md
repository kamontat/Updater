# jUpdater

# On Maintaination
- convert all `swing` to `javafx`

# To Contribute
1. run `mvn clean`
  - it's for clean all exist target folder
2. if you don't have folder `lib/` yet, please run `mvn initialize`
  - To download and install all necessary library
3. run `mvn install` for install, compile, production and deploy
    1. check library version
    2. install all library to `.m2/repository`
    3. compile .java to .class `target/classes`
    4. compile `default-jar file`
    5. compile `javadoc-jar file`
    6. compile `sources-jar file`
