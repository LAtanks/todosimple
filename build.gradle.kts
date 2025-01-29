plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.autonomousapps.dependency-analysis") version "1.28.0"
}

group = "me.learning"
version = "0.0.1"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

tasks.bootJar {
	manifest {
		attributes["Main-Class"] = "me.learning.TodoSimple.TodoSimpleApplication"
	}

	archiveBaseName.set("todosimple")
	archiveFileName.set("todosimple.jar")

}
tasks.jar{
	manifest {
		attributes["Main-Class"] = "me.learning.TodoSimple.TodoSimpleApplication"
	}

	archiveBaseName.set("todosimple")
	archiveFileName.set("todosimple.jar")
}
configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation ("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter")

	configurations.all {
		resolutionStrategy.eachDependency {
			if (requested.group == "org.springframework.boot") {
				useVersion("3.4.1")
			}
		}
	}

	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	runtimeOnly("com.h2database:h2")
	runtimeOnly("com.mysql:mysql-connector-j")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("org.apache.commons:commons-lang3")

	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
