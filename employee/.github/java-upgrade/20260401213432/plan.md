# Upgrade Plan: employee (20260401213432)

- **Generated**: 2026-04-02
- **HEAD Branch**: master
- **HEAD Commit ID**: N/A

## Available Tools

- JDKs
  - Java 25.0.2: `C:\Program Files\Java\jdk-25.0.2\bin` (target runtime)
  - Java 21.0.10
  - Java 17.0.18
- Build tools
  - Maven wrapper 3.9.14 via `.mvn/wrapper/maven-wrapper.properties`
  - No standalone Maven installation found

## Guidelines

- Upgrade the runtime to the latest LTS Java version available in the environment: Java 25.
- Preserve Spring Boot 4.0.4 unless compatibility issues require a controlled change.
- Prefer the Maven wrapper and only upgrade the build tool if needed for Java 25 support.
- Validate both compilation and tests before completing the upgrade.

## Options

- Working branch: appmod/java-upgrade-20260401213432
- Run tests before and after the upgrade: true

## Upgrade Goals

- Upgrade Java runtime from 17 to 25.

### Technology Stack

| Technology/Dependency | Current | Min Compatible | Why Incompatible |
| --------------------- | ------- | -------------- | ---------------- |
| Java | 17 | 25 | User requested latest LTS runtime |
| Spring Boot | 4.0.4 | 4.0.4 | Already compatible with Java 25 |
| Maven wrapper | 3.9.14 | 4.0.0 | Maven 3.9.x may not reliably support Java 25 |
| maven-compiler-plugin | implicit via Spring Boot parent | 3.11.0 | Required to compile Java 25 target release |
| maven-surefire-plugin | implicit via Spring Boot parent | 3.1.0 | Recommended for newer JDK module behavior |

### Derived Upgrades

- Upgrade `<java.version>` from `17` to `25` in `pom.xml`.
- Add explicit `maven-compiler-plugin` configuration with `<release>25`.
- Keep Spring Boot `4.0.4` unchanged unless a runtime compatibility or build tool issue requires a controlled upgrade.
- Verify Maven wrapper compatibility on Java 25 and upgrade to Maven 4.x if necessary.

## Upgrade Steps

- Step 1: Setup Environment
  - Rationale: Ensure the target JDK and build tool are available for the upgrade.
  - Changes to Make:
    - Confirm Java 25 is installed at `C:\Program Files\Java\jdk-25.0.2\bin`.
    - Verify that `mvnw.cmd` can execute under Java 25.
    - If the wrapper fails, install or upgrade to Maven 4.x as a fallback.
  - Verification:
    - Command: `mvnw.cmd -version` or fallback `mvn -version`
    - JDK: Java 25.0.2
    - Expected Result: Build tool starts successfully.

- Step 2: Setup Baseline
  - Rationale: Capture the current compilation and test baseline before changing the runtime.
  - Changes to Make:
    - Run the current Maven build lifecycle with the existing `pom.xml`.
    - Record baseline compilation and test results.
  - Verification:
    - Command: `mvnw.cmd clean test -q` (or fallback `mvn clean test -q`)
    - JDK: current default JDK available in environment
    - Expected Result: Baseline compile and tests complete successfully.

- Step 3: Apply Java 25 runtime upgrade
  - Rationale: Update the project to target Java 25 explicitly.
  - Changes to Make:
    - Set `<java.version>` to `25` in `pom.xml`.
    - Add explicit `maven-compiler-plugin` configuration to use `<release>25`.
    - Confirm no remaining Java 17 compatibility properties are present.
  - Verification:
    - Command: `mvnw.cmd clean test-compile -q`
    - JDK: Java 25.0.2
    - Expected Result: Project compiles successfully.

- Step 4: Validate Java 25 build
  - Rationale: Verify the upgraded runtime works for the full project including tests.
  - Changes to Make:
    - Run the full Maven test suite using Java 25.
    - Fix any compatibility or test failures introduced by the upgrade.
  - Verification:
    - Command: `mvnw.cmd clean test -q`
    - JDK: Java 25.0.2
    - Expected Result: All tests pass.

- Step 5: Final Validation
  - Rationale: Confirm that the upgrade is complete and the project is stable on Java 25.
  - Changes to Make:
    - Verify `pom.xml` reflects Java 25 and any build-tool updates.
    - Resolve all remaining TODOs and compatibility workarounds.
    - Ensure the working branch is clean and ready for commit.
  - Verification:
    - Command: `mvnw.cmd clean test -q`
    - JDK: Java 25.0.2
    - Expected Result: Compilation success and 100% test pass rate.

## Key Challenges

- Maven wrapper compatibility with Java 25
  - Strategy: Validate wrapper execution in Step 1 and upgrade to Maven 4.x if necessary.
- Java 25 compatibility with Boot 4.0.4
  - Strategy: Use explicit compiler plugin configuration and resolve any JDK-specific build failures.
- Preserving tests across the runtime jump
  - Strategy: Run the full suite after the upgrade and fix failures immediately.
