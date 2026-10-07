package com.atlas.bank.atlas_bank.archtest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
    packages = "com.atlas.bank.atlas_bank",
    importOptions = ImportOption.DoNotIncludeTests.class
)
public class HexagonalArchitectureTest {

  // ── Regla 1: El dominio no conoce la infraestructura ──

  @ArchTest
  static final ArchRule domain_should_not_depend_on_infrastructure =
      noClasses()
          .that().resideInAPackage("..domain..")
          .should().dependOnClassesThat()
          .resideInAPackage("..infrastructure..")
          .because("El dominio es el núcleo puro — no puede conocer la existencia de adaptadores " +
              "ni configuraciones");

  // ── Regla 2: El dominio no conoce la application ──

  @ArchTest
  static final ArchRule domain_should_not_depend_on_application =
      noClasses()
          .that().resideInAPackage("..domain..")
          .should().dependOnClassesThat()
          .resideInAPackage("..application..")
          .because("El dominio es el núcleo puro — no puede conocer la existencia de casos de uso" +
              " " +
              "ni servicios de aplicación");

  // ── Regla 3: La aplicación no conoce la infraestructura ──

  @ArchTest
  static final ArchRule application_should_not_depend_on_infrastructure =
      noClasses()
          .that().resideInAPackage("..application..")
          .should().dependOnClassesThat()
          .resideInAPackage("..infrastructure..")
          .because("La aplicación usa puertos, no puede conocer la existencia de adaptadores " +
              "ni configuraciones");

  // ── Regla 4: El dominio no depende de Spring ──
  @ArchTest
  static final ArchRule domain_should_not_depend_on_spring =
      noClasses()
          .that().resideInAPackage("..domain..")
          .should().dependOnClassesThat()
          .resideInAnyPackage("org.springframework..")
          .because("El dominio es el núcleo puro — no puede conocer la existencia de frameworks " +
              "externos como Spring");

//  @Test
//  void testDomainShouldNotDependOnInfrastructure() {
//    noClasses()
//        .that().resideInAPackage("..domain..")
//        .should().dependOnClassesThat()
//        .resideInAPackage("..infrastructure..")
//        .because("El dominio es el núcleo puro — no puede conocer la existencia de adaptadores " +
//            "ni configuraciones");
//  }
}