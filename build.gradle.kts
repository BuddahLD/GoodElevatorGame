import korlibs.korge.gradle.*

plugins {
	alias(libs.plugins.korge)
}

korge {
	id = "com.gmail.danylooliinyk.goodelevatorgame"
	
	targetJvm()
	targetAndroid()

	serializationJson()
}


dependencies {
    add("commonMainApi", project(":deps"))
    //add("commonMainApi", project(":korge-dragonbones"))
}

