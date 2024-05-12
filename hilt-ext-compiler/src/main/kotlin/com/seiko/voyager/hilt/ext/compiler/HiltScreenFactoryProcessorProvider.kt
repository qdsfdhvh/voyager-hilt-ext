package com.seiko.voyager.hilt.ext.compiler

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.seiko.voyager.hilt.ext.annotations.HiltScreenModel
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

class HiltScreenFactoryProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    return HiltScreenFactoryProcessor(environment)
  }
}

/**
 * https://voyager.adriel.cafe/screenmodel/hilt-integration
 * @HiltScreenModel 效果与 @HiltViewModel 类似，
 * 自动绑定 ScreenModel&ScreenModelFactory
 */
class HiltScreenFactoryProcessor(environment: SymbolProcessorEnvironment) : SymbolProcessor {

  private val codeGenerator: CodeGenerator = environment.codeGenerator
  private val logger = environment.logger

  override fun process(resolver: Resolver): List<KSAnnotated> {
    val visitor = HiltScreenModelVisitor()
    resolver.getSymbolsWithAnnotation(HILT_SCREEN_MODEL_NAME)
      .forEach { it.accept(visitor, Unit) }
    return emptyList()
  }

  inner class HiltScreenModelVisitor : KSVisitorVoid() {
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
      val moduleName = "${classDeclaration.simpleName.asString()}Module"
      FileSpec.builder(classDeclaration.packageName.asString(), moduleName)
        .addType(
          TypeSpec.classBuilder(moduleName)
            .addModifiers(KModifier.ABSTRACT)
            .addAnnotation(ClassDaggerModule)
            .addAnnotation(
              AnnotationSpec
                .builder(ClassDaggerInstallIn)
                .addMember("%T::class", ClassActivityComponent)
                .build(),
            ).apply {
              // annotation with KClass<*> will be crash by ksp proxy
              val annotation = classDeclaration.annotations.find {
                it.annotationType.resolve()
                  .toTypeName() == HiltScreenModel::class.asTypeName()
              } ?: run {
                logger.error("@HiltScreenModel annotation not found", classDeclaration)
                return@apply
              }
              val argumentValue = annotation.arguments
                .find { it.name?.getShortName() == "assistedFactory" }!!.value as KSType

              addFunction(
                FunSpec.builder("bind${classDeclaration.qualifiedName?.getShortName()}")
                  .addModifiers(KModifier.ABSTRACT)
                  .addAnnotation(ClassDaggerBinds)
                  .addAnnotation(ClassDaggerIntoMap)
                  .apply {
                    if (argumentValue.toTypeName() == UNIT) {
                      addAnnotation(
                        AnnotationSpec.builder(ClassScreenModelKey)
                          .addMember("%T::class", classDeclaration.toClassName())
                          .build(),
                      )
                      addParameter(
                        classDeclaration.simpleName.asString()
                          .replaceFirstChar { it.lowercase() },
                        classDeclaration.toClassName(),
                      )
                      returns(ClassScreenModel)
                    } else {
                      addAnnotation(
                        AnnotationSpec.builder(ClassScreenModelFactoryKey)
                          .addMember(
                            "%T::class",
                            argumentValue.toTypeName(),
                          )
                          .build(),
                      )
                      addParameter("factory", argumentValue.toTypeName())
                      returns(ClassScreenModelFactory)
                    }
                  }
                  .build(),
              )
            }.build(),
        )
        .build()
        .writeTo(
          codeGenerator = codeGenerator,
          dependencies = Dependencies(false, classDeclaration.containingFile!!),
        )
    }
  }

  companion object {
    private val HILT_SCREEN_MODEL_NAME =
      requireNotNull(HiltScreenModel::class.qualifiedName) {
        "Can not get qualifiedName for @HiltScreenModel"
      }
  }
}
