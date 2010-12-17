package mage.server.bdd;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * Defines Mage BDD steps.
 *
 * @author nantuko
 */
public class MageSteps {

    @Given("I'm in the game")
    public void inTheGame() {
        System.out.println("In the game");
    }

    @Given("I have an \"$card\" card")
    public void hasACard(String card) {
        System.out.println("card: " + card);
    }

    @Given("phase is $own \"$phase\"")
    public void hasPhase(String own, String phase) {
        System.out.println("phase is: " + own + " -> " + phase);
    }

    @When("I splay \"$card\"")
    public void playCard(String card) {
        System.out.println("play a card: " + card);
    }

    @Then("there is an \"$card\" on $zone")
    public void playCard(String card, String zone) {
        System.out.println("checking: " + card + ", zone=" + zone);
    }
}
