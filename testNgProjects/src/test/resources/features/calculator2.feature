@JiraTestId

Feature: Basic Arithmetic 2

  Background: A Calculator
    Given a calculator I just turned on

  Scenario: Addition
  # Try to change one of the values below to provoke a failure
    When I add -2 and 5
    Then the result is 3
