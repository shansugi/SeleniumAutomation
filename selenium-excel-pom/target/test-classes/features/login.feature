Feature: Login functionality using Excel test data

  Background:
    Given the user is on the login page

  # ── Inline data scenarios ─────────────────────────────────────────────────

  Scenario: Successful login with valid credentials (inline)
    When the user logs in with "standard_user" and "secret_sauce"
    Then the user should be on the home page

  Scenario: Login fails with invalid credentials (inline)
    When the user logs in with "standard_user" and "wrong_password"
    Then an error message "Epic sadface: Username and password do not match any user in this service" should be displayed

  # ── Excel-driven scenarios ────────────────────────────────────────────────

  Scenario: Successful login with data from Excel row 1
    When the user logs in using excel row 1
    Then the user should be on the home page

  Scenario: Failed login with data from Excel row 2
    When the user logs in using excel row 2
    Then the login should fail with the error from excel row 2

  Scenario: Failed login - locked out user from Excel row 3
    When the user logs in using excel row 3
    Then the login should fail with the error from excel row 3
