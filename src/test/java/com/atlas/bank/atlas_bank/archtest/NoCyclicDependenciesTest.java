package com.atlas.bank.atlas_bank.archtest;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
    packages = "com.atlas.bank.atlas_bank",
    importOptions = ImportOption.DoNotIncludeTests.class
)
public class NoCyclicDependenciesTest {

  @ArchTest
  static final ArchRule domain_modules_should_be_free_of_cycles =
      slices()
          .matching("com.atlas.bank.atlas_bank.domain.(*)..")
          .should().beFreeOfCycles()
          .because("No debe haber dependencias cíclicas entre los módulos del dominio");

  @ArchTest
  static final ArchRule no_cycles_between_packages =
      slices()
          .matching("com.atlas.bank.atlas_bank.(*)..")
          .should().beFreeOfCycles()
          .because("No debe haber dependencias cíclicas entre los paquetes del proyecto");
}
