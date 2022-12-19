@teacher
Feature: Add new team API and DB validation

  Scenario: Post new team and verify in database
    Given I logged Bookit api as a "teacher"
    When users send POST request to "/api/teams/team" with following info:
      | campus-location | VA       |
      | batch-number    | 26       |
      | team-name       | Eagles77 |
    Then status code should be 201
    And DataBase should persist same team info
    And User deletes previously added team
