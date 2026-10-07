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

public class SecurityIsolationTest {

  // ── Regla 1: El dominio no conoce la seguridad  ──
  @ArchTest
  static final ArchRule domain_should_not_depend_on_spring_security =
      noClasses()
          .that().resideInAPackage("..domain..")
          .should().dependOnClassesThat()
          .resideInAPackage("org.springframework.security..")
          .because("El dominio es el núcleo puro — no puede conocer la existencia de " +
              "configuraciones de seguridad");

  // ── Regla 1: El application no conoce la seguridad  ──
  @ArchTest
  static final ArchRule application_should_not_depend_on_spring_security =
      noClasses()
          .that().resideInAPackage("..application..")
          .should().dependOnClassesThat()
          .resideInAPackage("org.springframework.security..")
          .because("La aplicación es el núcleo puro — no puede conocer la existencia de " +
              "configuraciones de seguridad");


}
