grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
        // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
        //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

        // configure settings for the test-app JVM, uses the daemon by default
        test   : [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon: true],
        // configure settings for the run-app JVM
        run    : [maxMemory: 1024, minMemory: 256, debug: false, maxPerm: 256, forkReserve: false],
        // configure settings for the run-war JVM
        war    : [maxMemory: 1024, minMemory: 128, debug: false, maxPerm: 256, forkReserve: false],
        // configure settings for the Console UI JVM
        console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]
grails.project.dependency.resolver = "maven" // or ivy
//grails.plugin.location."vaadin-custom-security" = "C:\\\\maven-cache"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false
    // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        // runtime 'mysql:mysql-connector-java:5.1.29'
        // runtime 'org.postgresql:postgresql:9.3-1101-jdbc41'
        //compile 'javax.validation:validation-api:1.1.0.Final'

        //String springSecurityVersion = '3.2.9.RELEASE'
        String springSecurityVersion = '4.0.4.RELEASE'

        compile "org.springframework.security:spring-security-core:$springSecurityVersion", {
            excludes 'aopalliance', 'aspectjrt', 'cglib-nodep', 'commons-collections', 'commons-logging', 'ehcache', 'fest-assert', 'hsqldb', 'jcl-over-slf4j', 'jsr250-api', 'junit', 'logback-classic', 'mockito-core', 'powermock-api-mockito', 'powermock-api-support', 'powermock-core', 'powermock-module-junit4', 'powermock-module-junit4-common', 'powermock-reflect', 'spring-aop', 'spring-beans', 'spring-context', 'spring-core', 'spring-expression', 'spring-jdbc', 'spring-test', 'spring-tx'
        }

        test "org.grails:grails-datastore-test-support:1.0.2-grails-2.4"
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.55.2" // or ":tomcat:8.0.20"

        // plugins for the compile step
        compile ':cache:1.1.8'
        compile ":asset-pipeline:2.1.5"
        compile "org.grails.plugins:mongodb:6.0.6"
        compile ":vaadin:7.6.1"
        compile "org.grails.plugins:bcrypt:1.0"

        // plugins needed at runtime but not for compilation
        //runtime ":hibernate4:4.3.6.1"
        runtime ":database-migration:1.4.0"

    }
}
