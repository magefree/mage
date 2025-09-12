package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class ShadowOfTheGoblinTest extends CardTestPlayerBase {

    /*
    Shadow of the Goblin
    {1}{R}
    Enchantment
    Unreliable Visions -- At the beginning of your first main phase, discard a card. If you do, draw a card.
    Undying Vengeance -- Whenever you play a land or cast a spell from anywhere other than your hand, this enchantment deals 1 damage to each opponent.
    */
    private static final String shadowOfTheGoblin = "Shadow of the Goblin";

    /*
    Party Thrasher
    {1}{R}
    Creature - Lizard Wizard
    Noncreature spells you cast from exile have convoke.
    At the beginning of your precombat main phase, you may discard a card. If you do, exile the top two cards of your library, then choose one of them. You may play that card this turn.
    1/4
    */
    private static final String partyThrasher = "Party Thrasher";

    /*
    Misthollow Griffin
    {2}{U}{U}
    Creature - Griffin
    Flying
    You may cast Misthollow Griffin from exile.
    3/3
    */
    private static final String misthollowGriffin = "Misthollow Griffin";

    /*
    Glarb, Calamity's Augur
    {B}{G}{U}
    Legendary Creature - Frog Wizard Noble
    Deathtouch
    You may look at the top card of your library any time.
    You may play lands and cast spells with mana value 4 or greater from the top of your library.
    {T}: Surveil 2.
    2/4
    */
    private static final String glarbCalamitysAugur = "Glarb, Calamity's Augur";

    @Test
    public void testShadowOfTheGoblinFromHand() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, shadowOfTheGoblin);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Mountain");
        addCard(Zone.HAND, playerA, partyThrasher);

        setChoice(playerA, false); // shadow trigger
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, partyThrasher);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20); // no trigger
    }

    @Test
    public void testShadowOfTheGoblinFromExile() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, shadowOfTheGoblin);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, partyThrasher);
        addCard(Zone.HAND, playerA, "Mountain");
        addCard(Zone.EXILED, playerA, misthollowGriffin);

        setChoice(playerA, "<i>Unreliable Visions</i>");
        setChoice(playerA, true); // discard
        setChoice(playerA, "Mountain", 2);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, misthollowGriffin);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1 - 1); // land + spell from exile
    }

    @Test
    public void testShadowOfTheGoblinFromLibrary() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, shadowOfTheGoblin);
        addCard(Zone.BATTLEFIELD, playerA, glarbCalamitysAugur);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.LIBRARY, playerA, misthollowGriffin);
        addCard(Zone.LIBRARY, playerA, "Island");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, misthollowGriffin);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1 - 1); // land + spell from library
    }
}