package org.mage.test.cards.single.spm;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class ArachnePsionicWeaverTest extends CardTestPlayerBase {

    /*
    Arachne, Psionic Weaver
    {2}{W}
    Legendary Creature - Spider Human Hero
    Web-slinging {W}
    As Arachne enters, look at target opponent's hand, then choose a noncreature card type.
    Spells of the chosen type cost {1} more to cast.
    3/3
    */
    private static final String arachnePsionicWeaver = "Arachne, Psionic Weaver";

    /*
    Tormod's Crypt
    {0}
    Artifact
    {tap}, Sacrifice Tormod's Crypt: Exile all cards from target player's graveyard.
    */
    private static final String tormodsCrypt = "Tormod's Crypt";

    @Test
    public void testArachnePsionicWeaver() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, arachnePsionicWeaver);
        addCard(Zone.HAND, playerA, tormodsCrypt);
        addCard(Zone.HAND, playerB, tormodsCrypt);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arachnePsionicWeaver);
        setChoice(playerA, playerB.getName());
        setChoice(playerA, CardType.ARTIFACT.toString());

        checkPlayableAbility("Player A can't cast Tormod's", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + tormodsCrypt, false);

        checkPlayableAbility("Player B can cast Tormod's", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast " + tormodsCrypt, true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }
}
