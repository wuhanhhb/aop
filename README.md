# Usage

First include the build plugin:

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath 'com.sx.aop:plugin:1.0.1'
    }
}

You also can set the version of aspectj like this:

project {
    aspectjVersion = '1.8.0'
}

add this to your app or library:

apply plugin: 'aop'