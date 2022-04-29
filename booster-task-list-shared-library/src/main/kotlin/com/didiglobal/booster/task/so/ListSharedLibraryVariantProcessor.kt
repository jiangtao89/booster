package com.didiglobal.booster.task.so

import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.didiglobal.booster.gradle.project
import com.didiglobal.booster.task.spi.VariantProcessor
import com.google.auto.service.AutoService
import org.gradle.api.UnknownTaskException

private const val TASK_NAME = "listSharedLibraries"

@AutoService(VariantProcessor::class)
class ListSharedLibraryVariantProcessor : VariantProcessor {

    override fun process(variant: BaseVariant) {
        variant.project.tasks.let { tasks ->
            val listSharedLibraries = try {
                tasks.named(TASK_NAME)
            } catch (e: UnknownTaskException) {
                tasks.register(TASK_NAME)
            }
            tasks.register("list${variant.name.capitalize()}SharedLibraries", ListSharedLibrary::class.java) {
                it.outputs.upToDateWhen { false }
                it.variant = variant
            }.also {
                listSharedLibraries.dependsOn(it)
            }
        }
    }

}