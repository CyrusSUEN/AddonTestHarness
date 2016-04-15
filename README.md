# AddonTestHarness
Selenium-based testing for various browser addons

Usage:
```
$ mvn exec:java
```
OR
```
$ mvn exec:java -Dexec.args="MY_PROFILE_NAME"
```

## Testing ADN2 on Firefox

1. Create a new Firefox profile called ```cfxo``` and then go to ```about:config``` using the profile and set ```xpinstall.signatures.required``` to ```false```.
2. Install complied ADN2 XPI for the profile and go to ```http://rednoise.org/ad-auto-export``` for ads exportation.
3. Select ```Save file``` from the download prompt and check ```Do this automatically for files like this from now on.``` So that Firefox can download the exported JSON file automatically upon finishing the script.
4. Run PageVisitFirefoxTest.java as JUnit test and after the test there will be exported JSON file in your system's Download folder
