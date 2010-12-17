Scenario: player plays Land on his main phase

Given I'm in the game
And I have an "Island" card
And phase is my "Precombat Main"
When I play "Island"
Then there is an "Island" on battlefield