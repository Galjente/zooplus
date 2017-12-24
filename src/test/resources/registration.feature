Feature: new user registration
  Scenario: user with short password
    When user submit credentials test 1234
    Then get error message: Password to short

  Scenario: user success registration
    When user submit credentials test 1234password
    Then success registration
