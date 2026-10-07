package com.atlas.bank.atlas_bank.archtest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(
    packages = "com.atlas.bank.atlas_bank",
    importOptions = ImportOption.DoNotIncludeTests.class
)
public class NamingConventionTest {

  // ── Regla 1: Los controladores REST deben terminar con "Controller"
  // estar en el paquete "infrastructure.adapter.in.rest" ──

  @ArchTest
  static final ArchRule rest_controllers_should_reside_in_correct_package =
      classes()
          .that().haveSimpleNameEndingWith("Controller")
          .and().areAnnotatedWith(RestController.class)
          .should().resideInAPackage("..infrastructure.adapter.in..")
          .because("Los controladores REST deben terminar con 'Controller' y estar en el paquete " +
              "'infrastructure.adapter.in.rest'");

  // ── Regla 2: UseCase reside en el paquete "application.port.in" y terminan con "UseCase" ──
  @ArchTest
  static final ArchRule use_cases_should_reside_in_correct_package =
      classes()
          .that().haveSimpleNameEndingWith("UseCase")
          .should().resideInAPackage("..application.port.in..")
          .because("Los casos de uso deben terminar con 'UseCase' y estar en el paquete " +
              "'application.port.in'");

  // ── Regla 3: Los puertos de entrada deben ser interfaces
  @ArchTest
  static final ArchRule input_ports_should_be_interfaces =
      classes()
          .that().resideInAPackage("..application.port.in..")
          .should().beInterfaces()
          .because("Los puertos de entrada deben ser interfaces para permitir la implementación " +
              "por adaptadores");

  // ── Regla 4: Los puertos de salida deben ser interfaces
  @ArchTest
  static final ArchRule output_ports_should_be_interfaces =
      classes()
          .that().resideInAPackage("..application.port.out..")
          .should().beInterfaces()
          .because("Los puertos de salida deben ser interfaces para permitir la implementación " +
              "por adaptadores");


}
