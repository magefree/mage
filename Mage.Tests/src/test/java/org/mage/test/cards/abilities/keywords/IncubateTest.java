package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class IncubateTest extends CardTestPlayerBase {

    private void checkIncubate(String info, int needIncubateTokens, int needPhyrexianTokens, boolean needPlayableTransform) {
        checkPermanentCount(info, 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incubator Token", needIncubateTokens);
        checkPermanentCount(info, 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Token", needPhyrexianTokens);
        checkPlayableAbility(info, 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: Transform", needPlayableTransform);
    }

    @Test
    public void test_Transform_Normal() {
        // Incubate 3. (Create an Incubator token with three +1/+1 counters on it and “{2}: Transform this artifact.”
        // It transforms into a 0/0 Phyrexian artifact creature.)
        // Draw a card.
        addCard(Zone.HAND, playerA, "Eyes of Gitaxias", 1); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2); // for transform

        // prepare incubator 3
        checkIncubate("before", 0, 0, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eyes of Gitaxias");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkIncubate("after prepare", 1, 0, true);

        // transform
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: Transform");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkIncubate("after transform", 0, 1, false);
        checkPT("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Token", 3, 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Transform_Custom() {
        // target transform
        addCustomEffect_TransformTarget(playerA);

        // Alluring Suitor, 2/3
        // Deadly Dancer, 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Alluring Suitor", 1);

        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alluring Suitor", 1);

        // transform to back
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target transform", "Alluring Suitor");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after back", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alluring Suitor", 0);
        checkPermanentCount("after back", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deadly Dancer", 1);

        // transform to front
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target transform", "Deadly Dancer");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after front", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alluring Suitor", 1);
        checkPermanentCount("after front", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deadly Dancer", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Transform_IncubatorToken() {
        // target transform
        addCustomEffect_TransformTarget(playerA);

        // Incubate 3. (Create an Incubator token with three +1/+1 counters on it and “{2}: Transform this artifact.”
        // It transforms into a 0/0 Phyrexian artifact creature.)
        // Draw a card.
        addCard(Zone.HAND, playerA, "Eyes of Gitaxias", 1); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // prepare incubator 3
        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incubator Token", 0);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eyes of Gitaxias");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incubator Token", 1);

        // transform to back
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target transform", "Incubator Token");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after back", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Token", 1);

        // transform to front
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target transform", "Phyrexian Token");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after front", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incubator Token", 1);
        checkPermanentCount("after front", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Token", 0);

        // transform to back 2 (counters must be saved on transform, so it will be alive)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target transform", "Incubator Token");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after front 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incubator Token", 0);
        checkPermanentCount("after front 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Token", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Transform_CopiedByPermanent_FrontSide() {
        // use case: copy one side, can't tranform

        // target transform
        addCustomEffect_TransformTarget(playerA);
        // target destroy
        addCustomEffect_DestroyTarget(playerA);

        // Incubate 3. (Create an Incubator token with three +1/+1 counters on it and “{2}: Transform this artifact.”
        // It transforms into a 0/0 Phyrexian artifact creature.)
        // Draw a card.
        addCard(Zone.HAND, playerA, "Eyes of Gitaxias", 1); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        // You may have Copy Artifact enter the battlefield as a copy of any artifact on the battlefield,
        // except it’s an enchantment in addition to its other types.
        addCard(Zone.HAND, playerA, "Copy Artifact", 1); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        // prepare incubator 3
        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incubator Token", 0);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eyes of Gitaxias");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incubator Token", 1);

        // prepare copied front side
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Copy Artifact");
        setChoice(playerA, true); // use copy
        setChoice(playerA, "Incubator Token");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incubator Token", 2);

        // kill original token
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy");
        addTarget(playerA, "Incubator Token[no copy]");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after kill", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incubator Token", 1);
        showBattlefield("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // try to transform (nothing happen)
        // 701.28c
        // If a spell or ability instructs a player to transform a permanent that isn’t represented by a
        // transforming token or a transforming double-faced card, nothing happens.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target transform", "Incubator Token");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after transform", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incubator Token", 1);
        checkPermanentCount("after transform", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Token", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Transform_CopiedByPermanent_BackSide() {
        // use case: copy one side, can't tranform

        // target transform
        addCustomEffect_TransformTarget(playerA);
        // target destroy
        addCustomEffect_DestroyTarget(playerA);

        // Incubate 3. (Create an Incubator token with three +1/+1 counters on it and “{2}: Transform this artifact.”
        // It transforms into a 0/0 Phyrexian artifact creature.)
        // Draw a card.
        addCard(Zone.HAND, playerA, "Eyes of Gitaxias", 1); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        // You may have Copy Artifact enter the battlefield as a copy of any artifact on the battlefield,
        // except it’s an enchantment in addition to its other types.
        addCard(Zone.HAND, playerA, "Copy Artifact", 1); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        // prepare incubator 3
        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incubator Token", 0);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eyes of Gitaxias");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incubator Token", 1);

        // transform to back
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target transform", "Incubator Token");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after back", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Token", 1);

        // prepare copied back side
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Copy Artifact");
        setChoice(playerA, true); // use copy
        setChoice(playerA, "Phyrexian Token");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Token", 2);

        // kill original token
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy");
        addTarget(playerA, "Phyrexian Token[no copy]");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after kill", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Token", 1);
        showBattlefield("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // try to transform back side (nothing happen)
        // 701.28c
        // If a spell or ability instructs a player to transform a permanent that isn’t represented by a
        // transforming token or a transforming double-faced card, nothing happens.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target transform", "Phyrexian Token");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after transform", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incubator Token", 0);
        checkPermanentCount("after transform", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Token", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
