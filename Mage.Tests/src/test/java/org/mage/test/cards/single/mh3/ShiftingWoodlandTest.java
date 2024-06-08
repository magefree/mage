package org.mage.test.cards.single.mh3;

import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public class ShiftingWoodlandTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.ShiftingWoodland Shifting Woodland} Shifting Woodland
     * Land
     * Shifting Woodland enters the battlefield tapped unless you control a Forest.
     * {T}: Add {G}.
     * Delirium — {2}{G}{G}: Shifting Woodland becomes a copy of target permanent card in your graveyard until end of turn. Activate only if there are four or more card types among cards in your graveyard.
     */
    private static final String woodland = "Shifting Woodland";

    @Test
    public void test_NoDelirium() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, woodland);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Plains");
        addCard(Zone.GRAVEYARD, playerA, "Memnite");

        checkPlayableAbility("no Delirium condition", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Delirium</i> &mdash; {2}{G}{G}:", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void test_Copy_StopEndOfTurn() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Yavimaya Coast", 4); // to be sure not to activate Woodland
        addCard(Zone.BATTLEFIELD, playerA, woodland);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Plains");
        addCard(Zone.GRAVEYARD, playerA, "Memnite");
        addCard(Zone.GRAVEYARD, playerA, "Divination");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}. {this} deals 1 damage to you.", 4);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Delirium</i> &mdash; {2}{G}{G}:", "Grizzly Bears");

        attack(1, playerA, "Grizzly Bears", playerB);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20 - 4);
        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerA, woodland, 1);
        assertType(woodland, CardType.CREATURE, false);
        assertType(woodland, CardType.LAND, true);
        assertColor(playerA, woodland, "Green", false);
    }

    @Ignore // CopyEffect is not discarded properly. See #12433
    @Test
    public void test_Copy_StopOnLTB() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Yavimaya Coast", 4); // to be sure not to activate Woodland
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, woodland);
        addCard(Zone.HAND, playerA, "Shoot the Sheriff");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Plains");
        addCard(Zone.GRAVEYARD, playerA, "Memnite");
        addCard(Zone.GRAVEYARD, playerA, "Divination");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}. {this} deals 1 damage to you.", 4);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Delirium</i> &mdash; {2}{G}{G}:", "Grizzly Bears");

        attack(1, playerA, "Grizzly Bears", playerB);

        castSpell(1, PhaseStep.END_COMBAT, playerA, "Shoot the Sheriff", "Grizzly Bears", true);

        runCode("check that the copy effect ended", 1, PhaseStep.END_COMBAT, playerA, (info, player, game) -> {
            Card card = player.getGraveyard().getCards(game).stream().filter(c -> c.getName().equals(woodland)).findFirst().orElse(null);
            if (card == null) {
                Assert.fail("Shifting Woodland is not in the graveyard");
            }
            List<String> failReasons = new ArrayList<>();
            if (!card.isLand(game) || card.getCardType(game).size() != 1) {
                String type = card.getCardType(game).stream().map(CardType::toString).collect(Collectors.joining(", "));
                failReasons.add("types is not right, should be back to land, but is [" + type + "]");
            }
            if (!card.getSubtype(game).isEmpty()) {
                String type = card.getSubtype(game).stream().map(SubType::toString).collect(Collectors.joining(", "));
                failReasons.add("subtypes is not right, should be back to none, but is [" + type + "]");
            }
            if (!card.getColor(game).isColorless()) {
                failReasons.add("color is not right, should be back to colorless, but is " + card.getColor(game));
            }
            if (!failReasons.isEmpty()) {
                String failText = "Shifting Woodland's copy effect did not end properly:\n\t&mdash; " + String.join("\n\t&mdash; ", failReasons);
                Assert.fail(failText);
            }
        });

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 4);
        assertLife(playerB, 20 - 2);
        assertGraveyardCount(playerA, woodland, 1);
    }
}
