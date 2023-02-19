plugins {
    kotlin("multiplatform") version "1.8.0"
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
}

group = "com.github.d-costa"
version = "0.0.1"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        browser()
        nodejs()
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }
    
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }
}

koverMerged {
    enable()
}

tasks.register("koverPrintMergedXmlCoverage") {
    val koverMergedXmlReport = tasks.named("koverMergedXmlReport")
    dependsOn(koverMergedXmlReport)
    doLast {
        //language=RegExp
        val regexp = """<counter type="INSTRUCTION" missed="(\d+)" covered="(\d+)"/>""".toRegex()
        koverMergedXmlReport.get().outputs.files.forEach { file ->
            // Read file by lines
            file.useLines { lines ->
                // Last line in file that matches regexp is the total coverage
                lines.last(regexp::containsMatchIn).let { line ->
                    // Found the match
                    regexp.find(line)?.let {
                        val missed = it.groupValues[1].toInt()
                        val covered = it.groupValues[2].toInt()
                        println("Total Code Coverage: ${covered * 100 / (missed + covered)}%")
                    }
                }
            }
        }
    }
}