Feature: user login
  Scenario: login with not existing user
    When login as test@test.test 1234
    Then get error message: Incorrect credential

  Scenario: login with incorrect password
    When login as admin@test.test 1234
    Then get error message: Incorrect credential

  Scenario: successfully login
    When login as admin@test.test admin1234
    Then success login