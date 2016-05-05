# AddonTestHarness

## Testing AdNauseam (v2.*) on Firefox via Eclipse

1. Create a new Firefox profile called ```MY_PROFILE_NAME``` and then go to ```about:config``` using the profile and set ```xpinstall.signatures.required``` to ```false```.

2. Install compiled AdNauseam XPI using the profile and go to ```http://rednoise.org/ad-auto-export``` to export the ads.

3. Select ```Save file``` from the download prompt and check ```Do this automatically for files like this from now on.``` So that Firefox will download the exported JSON file automatically upon finishing the script.

4. Run ath.test.adnauseum.PageVisitFirefoxTest as a JUnit test. When it is finished, there will an be exported JSON file in your browser's default download folder
