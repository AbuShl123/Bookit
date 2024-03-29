Feature: User Verification

  @wip
  Scenario: verify information about logged user
    Given I logged Bookit api as a "team-leader"
    When I sent get request to "/api/users/me" endpoint
    Then status code should be 200
    And content type is "application/json"
    And role is "student-team-leader"

  @wip @db @ui
  Scenario: three point/layer (UI, API, DATABASE)
    Given user logs in using "team-leader" credentials
    And user is on the my self page
    And I logged Bookit api as a "team-leader"
    When I sent get request to "/api/users/me" endpoint
    Then UI, API, and DataBase user information must be match

  @wip @db @ui
  Scenario Outline: three point/layer (UI, API, DATABASE)
    Given user logs in using "<role>" credentials
    And user is on the my self page
    And I logged Bookit api as a "<role>"
    When I sent get request to "/api/users/me" endpoint
    Then UI, API, and DataBase user information must be match

    Examples:
      | role        |
      | teacher     |
      | team-leader |
      | team-member |