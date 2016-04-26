# Info

inject the method by Annotation, you can change its work way with it

# Usage

First include the build plugin to your app or library:

    buildscript {
        repositories {
            jcenter()
        }
        dependencies {
            classpath 'com.sx.aop:plugin:1.1.1'
        }
    }
    apply plugin: 'com.android.application'
    apply plugin: 'aop'


You maybe should to special the application's build gradle's version :
    
    buildscript {
        repositories {
            jcenter()
        }
        dependencies {
            classpath 'com.android.tools.build:gradle:1.3.1'//local test pass
            //classpath 'com.android.tools.build:gradle:2.1.0-beta3'
            // NOTE: Do not place your application dependencies here; they belong
            // in the individual module build.gradle files
        }
    }
    
the version(2.1.0-beta3) will throw java.lang.Verify Error exception, but 1.3.1 is fine on local test!

# Demo

add inject like this:
    
        //add Inject
        AopManager.addInject(MainActivity.class, new AopManager.Inject() {
            @Override
            public boolean before(String methodName) {
                log("before:" + methodName);
                return "add".equals(methodName);
            }

            @Override
            public Object process(String methodName, Object... params) {
                log("process:" + params[0]);
                return (Integer) params[0] * 2;
            }

            @Override
            public Object after(String methodName, Object result) {
                log("after:" + result);
                if (result == null) return null;
                return (Integer) result * 2;
            }
        });
        //test it!
        test();
        log("add(10)'s result is : " + add(10));
        
the method of test and add is simple:

    @Aop
    public void test() {
        log("test");
    }

    @Aop
    public int add(int i) {
        return i + 10;
    }
    
the result will out print:

    before:test
    test
    after:null
    before:add
    process:10
    after:20
    add(10)'s result is : 40
    
more info see the Demo/SimpleDemo.