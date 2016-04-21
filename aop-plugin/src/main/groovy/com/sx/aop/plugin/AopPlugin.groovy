package com.sx.aop.plugin

import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException
import org.gradle.api.tasks.compile.JavaCompile

/**
 * learn from https://github.com/casidiablo/gradle-android-aspectj
 */
class AopPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def variants = null
        //com.android.application
        if (project.plugins.findPlugin("android") || project.plugins.findPlugin("com.android.application")) {
            variants = "applicationVariants"
        } else if (project.plugins.findPlugin("android-library") || project.plugins.findPlugin("com.android.library")) {
            variants = "libraryVariants"
        } else {
            throw new ProjectConfigurationException("The android or android-library plugin must be applied to the project", null);
        }

        final def log = project.logger

        project.extensions.create('aop', AopExtension)

        println "current aspectjVersion : ${project.aop.aspectjVersion}"
        project.dependencies {
            compile "com.sx.aop:runtime:1.0.1"
            compile "org.aspectj:aspectjrt:${project.aop.aspectjVersion}"
        }

        project.afterEvaluate {
            project.android[variants].all {
                variant ->

                    JavaCompile javaCompile = variant.javaCompile
                    javaCompile.doLast {
                        String[] args = [
                                "-showWeaveInfo",
                                "-1.5",
                                "-inpath", javaCompile.destinationDir.toString(),
                                "-aspectpath", javaCompile.classpath.asPath,
                                "-d", javaCompile.destinationDir.toString(),
                                "-classpath", javaCompile.classpath.asPath,
                                "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)
                        ]
                        log.debug "ajc args: " + Arrays.toString(args)

                        MessageHandler handler = new MessageHandler(true);
                        new Main().run(args, handler);
                        for (IMessage message : handler.getMessages(null, true)) {
                            switch (message.getKind()) {
                                case IMessage.ABORT:
                                case IMessage.ERROR:
                                case IMessage.FAIL:
                                    log.error message.message, message.thrown
                                    break;
                                case IMessage.WARNING:
                                    log.warn message.message, message.thrown
                                    break;
                                case IMessage.INFO:
                                    log.info message.message, message.thrown
                                    break;
                                case IMessage.DEBUG:
                                    log.debug message.message, message.thrown
                                    break;
                            }
                        }
                    }
            }
        }
    }
}
