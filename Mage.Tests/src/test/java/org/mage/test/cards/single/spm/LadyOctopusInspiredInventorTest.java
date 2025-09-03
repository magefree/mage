package org.mage.test.cards.single.spm;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class LadyOctopusInspiredInventorTest extends CardTestPlayerBase {

    /*
    Lady Octopus, Inspired Inventor
    {U}
    Legendary Creature - Human Scientist Villain
    Whenever you draw your first or second card each turn, put an ingenuity counter on Lady Octopus.
    {T}: You may cast an artifact spell from your hand with mana value less than or equal to the number of ingenuity counters on Lady Octopus without paying its mana cost.
    */
    private static final String ladyOctopusInspiredInventor = "Lady Octopus, Inspired Inventor";

    /*
    Aether Vial
    {1}
    Artifact
    At the beginning of your upkeep, you may put a charge counter on Aether Vial.
    {T}: You may put a creature card with converted mana cost equal to the number of charge counters on  ther Vial from your hand onto the battlefield.
    */
    private static final String aetherVial = "Aether Vial";

    /*
    Tormod's Crypt
    {0}
    Artifact
    {tap}, Sacrifice Tormod's Crypt: Exile all cards from target player's graveyard.
    */
    private static final String tormodsCrypt = "Tormod's Crypt";

    /*
    Howling Mine
    {2}
    Artifact
    At the beginning of each player's draw step, if Howling Mine is untapped, that player draws an additional card.
    */
    private static final String howlingMine = "Howling Mine";

    @Test
    public void testLadyOctopusInspiredInventor() {
        setStrictChooseMode(true);

        addCustomCardWithAbility("untap all creatures", playerA, new SimpleActivatedAbility(
                new UntapAllControllerEffect(new FilterCreaturePermanent()),
                new ManaCostsImpl<>("")
        ));
        addCustomCardWithAbility("draw a card", playerA, new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new ManaCostsImpl<>("")
        ));
        addCard(Zone.BATTLEFIELD, playerA, ladyOctopusInspiredInventor);
        addCard(Zone.HAND, playerA, aetherVial);
        addCard(Zone.HAND, playerA, tormodsCrypt);
        addCard(Zone.HAND, playerA, howlingMine);

        activateDrawCardAndUntap(); // Tormod's crypt
        activateDrawCardAndUntap(); // Aether Vial
        activateDrawCardAndUntap(); // Howling Mine

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, ladyOctopusInspiredInventor, CounterType.INGENUITY, 2);
        assertHandCount(playerA, 3);
    }

    private void activateDrawCardAndUntap() {
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: You may cast");
        setChoice(playerA, true);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "draw a");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "untap all");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
    }

    @Test
    public void testLadyOctopusInspiredInventorChoose() {
        setStrictChooseMode(true);

        addCustomCardWithAbility("draw a card", playerA, new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(3),
                new ManaCostsImpl<>("")
        ));
        addCard(Zone.BATTLEFIELD, playerA, ladyOctopusInspiredInventor);
        addCard(Zone.HAND, playerA, aetherVial);
        addCard(Zone.HAND, playerA, tormodsCrypt);
        addCard(Zone.HAND, playerA, howlingMine);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "draw ");
        setChoice(playerA, "Whenever you draw your first"); // trigger stack
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: You may cast");
        setChoice(playerA, tormodsCrypt);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, ladyOctopusInspiredInventor, CounterType.INGENUITY, 2);
        assertHandCount(playerA, 3 + 2);
    }
}