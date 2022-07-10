# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

########################################################### Retrofit2

-keep public class org.slf4j.** { *; }
-keep public class ch.qos.** { *; }
-keep public class org.apache.** { *; }
-keep class com.ibm.hrl.datacapArfl.ArActivity { *; }
-keep class com.ibm.ecm.capture.** { *; }
-keep class com.googlecode.tesseract.android.** { *; }
-keep class com.fiberlink.** { *; }
-keep class com.ibm.androidsampleapplication.model.**{*;}

-keep class org.xmlpull.v1.** { *; }

-keep class com.android.volley.** { *; }
-keep class org.apache.commons.logging.**

-keepattributes *Annotation*

-dontwarn org.apache.**

-dontwarn com.squareup.picasso.**
-dontwarn butterknife.internal.**
-dontwarn org.apache.**
-dontwarn com.ning.http.**
-dontwarn ch.qos.logback.**
-dontwarn org.bouncycastle.**
-dontwarn org.apache.http.impl.auth.**
-dontwarn com.fiberlink.maas360sdk.**
-dontwarn com.fiberlink.maas360.**
-dontwarn com.ibm.ecm.navigator.mdm.**
-dontwarn com.fasterxml.jackson.databind.**
-dontwarn android.net.http.**
-dontwarn javax.**
-dontwarn lombok.**
-dontwarn org.apache.**
-dontwarn com.squareup.**
-dontwarn com.sun.**
-dontwarn **retrofit**
-dontwarn **okio**
-dontwarn com.ibm.**

-dontwarn org.xmlpull.v1.**
-keep class retrofit.** { *; }
#-keep class resources.**{*;}
#-keep class sources.** {*;}
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
    }

######################################################################## Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# Uncomment for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
################################################################################Room
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**