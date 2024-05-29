plugins {
    id("com.android.application")
}

android {
    namespace = "com.fabiansuarez.tiendavirtual"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fabiansuarez.tiendavirtual"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.2")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //permite mostrar el slider en el home para las promociones
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    //implementacion de los colores ===> https://github.com/yukuku/ambilwarna
    implementation("com.github.yukuku:ambilwarna:2.0.1")
    //permite cargar imagenes de internet
    implementation("com.squareup.picasso:picasso:2.71828")

    implementation("com.google.code.gson:gson:2.8.8")
    //permite poner imagnees redondas
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation ("androidx.camera:camera-core:1.1.0")
    implementation ("androidx.camera:camera-camera2:1.1.0")
    implementation ("androidx.camera:camera-lifecycle:1.1.0")
    implementation ("androidx.camera:camera-view:1.0.0-alpha27")
    implementation ("org.tensorflow:tensorflow-lite-task-vision:0.4.0")
    // Import the GPU delegate plugin Library for GPU inference
    implementation ("org.tensorflow:tensorflow-lite-gpu-delegate-plugin:0.4.0")
    implementation ("org.tensorflow:tensorflow-lite-gpu:2.9.0")
    // CameraX core library

    implementation ("androidx.camera:camera-core:$1.2.0-alpha02")
    // CameraX Camera2 extensions
    implementation ("androidx.camera:camera-camera2:$1.2.0-alpha02")

    // CameraX Lifecycle library
    implementation ("androidx.camera:camera-lifecycle:$1.2.0-alpha02")

    // CameraX View class
    implementation ("androidx.camera:camera-view:$1.2.0-alpha02")
    dependencies {
        implementation ("org.tensorflow:tensorflow-lite:2.9.0")
        implementation ("org.tensorflow:tensorflow-lite-support:0.3.1")
    }


}

