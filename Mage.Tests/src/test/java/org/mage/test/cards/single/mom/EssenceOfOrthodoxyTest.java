package org.mage.test.cards.single.mom;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class EssenceOfOrthodoxyTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.e.EssenceOfOrthodoxy Essence of Orthodoxy} {3}{W}{W}
     * Creature — Phyrexian Spirit
     * Flying
     * Whenever Essence of Orthodoxy or another Phyrexian you control enters, incubate 2.
     */
    private static final String essence = "Essence of Orthodoxy";

    /**
     * {@link mage.cards.e.EleshNorn Elesh Norn // The Argent Etchings}
     * {2}{W}, Sacrifice three other creatures: Exile Elesh Norn, then return it to the battlefield
     * transformed under its owner's control. Activate only as a sorcery.
     * I — Incubate 2 five times, then transform all Incubator tokens you control.
     */
    private static final String eleshNorn = "Elesh Norn";

    @Test
    public void test_ArgentEtchings_TransformedIncubatorsMustNotTrigger() {
        // bug: https://github.com/magefree/mage/issues/16630
        // Incubator tokens enter as non-Phyrexian artifacts and are transformed only afterwards,
        // so their enter triggers must be checked against the front face
        addCard(Zone.BATTLEFIELD, playerA, essence, 1);
        addCard(Zone.BATTLEFIELD, playerA, eleshNorn, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{W}, Sacrifice");
        setChoice(playerA, "Silvercoat Lion^Silvercoat Lion^Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "The Argent Etchings", 1);
        assertPermanentCount(playerA, "Phyrexian Token", 5);
        assertPermanentCount(playerA, "Incubator Token", 0);
        assertPowerToughness(playerA, "Phyrexian Token", 2, 2, Filter.ComparisonScope.All);
    }

    @Test
    public void test_Incubate_FrontFaceMustNotTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, essence, 1);
        // Incubate 3. Draw a card.
        addCard(Zone.HAND, playerA, "Eyes of Gitaxias", 1); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eyes of Gitaxias");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Incubator Token", 1);
        assertPermanentCount(playerA, "Phyrexian Token", 0);
    }

    @Test
    public void test_PhyrexianBackFaceEntering_MustTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, essence, 1);
        // Incubate 3. Draw a card.
        addCard(Zone.HAND, playerA, "Eyes of Gitaxias", 1); // {2}{U}
        // You may have Copy Artifact enter the battlefield as a copy of any artifact on the battlefield,
        // except it's an enchantment in addition to its other types.
        addCard(Zone.HAND, playerA, "Copy Artifact", 1); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eyes of Gitaxias");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // transforming is not entering, so no trigger here
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: Transform");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after transform", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Token", 1);
        checkPermanentCount("after transform", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incubator Token", 0);

        // a copy of the back face enters as a Phyrexian and triggers (then dies as 0/0)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Copy Artifact");
        setChoice(playerA, true); // use copy
        setChoice(playerA, "Phyrexian Token");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Phyrexian Token", 1);
        assertPermanentCount(playerA, "Incubator Token", 1);
    }
}
