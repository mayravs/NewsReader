# Fix localeConfig Build Error

The project currently has a build error because the resource referenced by `android:localeConfig` in `AndroidManifest.xml` (`@xml/locales_config`) is defined in locale-specific directories (`res/xml-en/` and `res/xml-es-rMX/`). Resources referenced from the manifest (except for version qualifiers) cannot vary by configuration. Additionally, the current content of these files uses `<PreferenceScreen>` instead of the required `<locale-config>` tag.

## User Review Required

> [!IMPORTANT]
> This change will consolidate the supported locales into a single file and remove the redundant, incorrectly formatted files.

## Proposed Changes

### Android Resources

Summary of changes to fix the `localeConfig` issue.

---

#### [NEW] [locales_config.xml](file:///Users/mayra/AndroidStudioProjects/NewsReader/app/src/main/res/xml/locales_config.xml)
Create a new, correctly formatted `locales_config.xml` in the base `res/xml/` directory.

#### [DELETE] [locales_config.xml (en)](file:///Users/mayra/AndroidStudioProjects/NewsReader/app/src/main/res/xml-en/locales_config.xml)
Remove the incorrectly placed and formatted file.

#### [DELETE] [locales_config.xml (es-rMX)](file:///Users/mayra/AndroidStudioProjects/NewsReader/app/src/main/res/xml-es-rMX/locales_config.xml)
Remove the incorrectly placed and formatted file.

## Verification Plan

### Automated Tests
- I will run `analyze_file` on `AndroidManifest.xml` again to ensure the error is resolved.
- I will attempt a gradle build to verify the fix.

### Manual Verification
- N/A
