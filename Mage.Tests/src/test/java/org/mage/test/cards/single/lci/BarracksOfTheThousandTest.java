package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BarracksOfTheThousandTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.ThousandMoonsSmithy} {2}{W}{W} <br>
     * Legendary Artifact <br>
     * When Thousand Moons Smithy enters the battlefield, create a white Gnome Soldier artifact creature token with “This creature’s power and toughness are each equal to the number of artifacts and/or creatures you control.”  <br>
     * At the beginning of your precombat main phase, you may tap five untapped artifacts and/or creatures you control. If you do, transform Thousand Moons Smithy.
     */
    private static final String smithy = "Thousand Moons Smithy";
    /**
     * {@link mage.cards.b.BarracksOfTheThousand} <br>
     * Legendary Artifact Land <br>
     * {T}: Add {W}. <br>
     * Whenever you cast an artifact or creature spell using mana produced by Barracks of the Thousand, create a white Gnome Soldier artifact creature token with “This creature’s power and toughness are each equal to the number of artifacts and/or creatures you control.” <br>
     */
    private static final String barracks = "Barracks of the Thousand";

    private void initToTransform() {
        addCard(Zone.BATTLEFIELD, playerA, smithy);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Bear Cub");
        addCard(Zone.BATTLEFIELD, playerA, "Forest Bear");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear");

        // First mainphase, transform the smithy tapping all the bears
        setChoice(playerA, true); // yes to "you may tap"
        setChoice(playerA, "Balduvian Bears^Bear Cub^Forest Bear^Grizzly Bears^Runeclaw Bear");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
    }

    @Test
    public void trigger_simple() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Arcbound Worker", 1);
        initToTransform();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcbound Worker");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Gnome Soldier Token", 1);
        assertPermanentCount(playerA, "Arcbound Worker", 1);
    }

    @Test
    public void trigger_onlyonce_doublemana() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Heartbeat of Spring");
        addCard(Zone.HAND, playerA, "Armored Warhorse");
        initToTransform();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Armored Warhorse");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Gnome Soldier Token", 1);
        assertPermanentCount(playerA, "Armored Warhorse", 1);
    }


    @Test
    public void noTrigger_NotPaidWithBarrack() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Memnite", 1);
        initToTransform();

        // Memnite cost 0 so no mana is spend from Barracks.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Gnome Soldier Token", 0);
        assertPermanentCount(playerA, "Memnite", 1);
    }

    @Test
    public void noTrigger_notCheck() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Divination", 1);
        initToTransform();

        // Sorcery, doesn't trigger Barrack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divination");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Gnome Soldier Token", 0);
        assertGraveyardCount(playerA, "Divination", 1);
    }

    @Test
    public void noTrigger_afterRemand() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerB, "Remand");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        addCard(Zone.HAND, playerA, "Arcbound Worker");
        addCard(Zone.HAND, playerA, "Plains", 1); // to cast the second time
        initToTransform();

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcbound Worker");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Remand", "Arcbound Worker", "Arcbound Worker");

        checkGraveyardCount("Remand in graveyard", 3, PhaseStep.BEGIN_COMBAT, playerB, "Remand", 1);
        checkHandCardCount("Worker in hand", 3, PhaseStep.BEGIN_COMBAT, playerA, "Arcbound Worker", 1);
        checkPermanentCount("Gnome Soldier Token on battlefield", 3, PhaseStep.BEGIN_COMBAT, playerA, "Gnome Soldier Token", 1);

        playLand(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Plains");
        waitStackResolved(3, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Arcbound Worker");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Gnome Soldier Token", 1);
        assertPermanentCount(playerA, "Arcbound Worker", 1);
        assertGraveyardCount(playerB, "Remand", 1);
    }

}
