package org.mage.test.cards.abilities;

import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IntensityAbility;
import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.GameState;
import mage.util.PerpetualUtil;

import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AlchemyPerpetualTest extends CardTestPlayerBase {

    @Test
    public void testPerpetualChangesFollowACopyButAreNotCopied() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Clone");
        addCard(Zone.HAND, playerB, "Clone");
        addCard(Zone.HAND, playerB, "Unsummon");
        runCode("perpetual copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Card clone = player.getHand().getCards(game).iterator().next();
            PerpetualUtil.boost(clone, 1, 1, clone.getSpellAbility(), game);
            PerpetualUtil.addAbility(clone, HasteAbility.getInstance(), clone.getSpellAbility(), game);
        });
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true);
        setChoice(playerA, "Grizzly Bears");
        castSpell(2, PhaseStep.UPKEEP, playerB, "Unsummon", "Grizzly Bears[no copy]");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, true);
        setChoice(playerB, "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
        assertAbility(playerA, "Grizzly Bears", HasteAbility.getInstance(), true);
        assertPowerToughness(playerB, "Grizzly Bears", 2, 2);
        assertAbility(playerB, "Grizzly Bears", HasteAbility.getInstance(), false);
    }

    @Test
    public void testPerpetualBoostAppliesAfterBasePowerChanges() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Turn to Frog");
        runCode("perpetual boost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Card bears = game.getBattlefield().getAllActivePermanents(player.getId()).iterator().next();
            PerpetualUtil.boost(bears, 1, 1, bears.getSpellAbility(), game);
        });
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Turn to Frog", "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
    }

    @Test
    public void testConjuredDuplicateDoesNotInheritPerpetualChanges() {
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerB, "Silvercoat Lion");
        runCode("duplicate specialization", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Card original = player.getHand().getCards(game).iterator().next();
            PerpetualUtil.addAbility(original, HasteAbility.getInstance(), original.getSpellAbility(), game);
            PerpetualUtil.boost(original, 1, 1, original.getSpellAbility(), game);
            IntensityAbility.intensify(original, 2, game);
            Card duplicate = ConjureCardEffect.createCard(original.getName());
            game.loadCards(java.util.Collections.singleton(duplicate), player.getId());
            game.applyEffects();
            Card changed = game.getCard(duplicate.getId());
            Assert.assertNotEquals(original.getId(), changed.getId());
            Assert.assertEquals(2, changed.getPower().getBaseValue());
            Assert.assertFalse(changed.getAbilities(game).containsClass(HasteAbility.class));
            Assert.assertEquals(0, IntensityAbility.getIntensity(changed, game));
        });
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void testChangesAreCopiedAndRestoredWithoutMutatingOriginalCard() {
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        runCode("perpetual snapshots", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Card original = player.getHand().getCards(game).iterator().next();
            PerpetualUtil.boost(original, 1, 1, original.getSpellAbility(), game);
            GameState saved = game.getState().copy();
            PerpetualUtil.boost(original, 1, 1, original.getSpellAbility(), game);

            Assert.assertEquals(2, original.getPower().getBaseValue());
            Assert.assertEquals(3, saved.getPerpetualCard(original.getId()).getPower().getBaseValue());
            Assert.assertEquals(4, game.getCard(original.getId()).getPower().getBaseValue());

            GameState restored = game.getState().copy();
            restored.restore(saved);
            Assert.assertEquals(3, restored.getPerpetualCard(original.getId()).getPower().getBaseValue());
            Assert.assertEquals(Zone.HAND, restored.getZone(original.getId()));
        });
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void testSpecializationPreservesExistingPerpetualChangesAndIdentity() {
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerB, "Silvercoat Lion");
        runCode("specialization", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Card original = player.getHand().getCards(game).iterator().next();
            Card form = game.getPlayer(playerB.getId()).getHand().getCards(game).iterator().next();
            PerpetualUtil.addAbility(original, HasteAbility.getInstance(), original.getSpellAbility(), game);
            PerpetualUtil.boost(original, 1, 1, original.getSpellAbility(), game);
            PerpetualUtil.specialize(original, form, game);
            game.applyEffects();

            Card changed = game.getCard(original.getId());
            Assert.assertEquals(original.getId(), changed.getId());
            Assert.assertEquals(player.getId(), changed.getOwnerId());
            Assert.assertEquals("Silvercoat Lion", changed.getName());
            Assert.assertEquals(3, changed.getPower().getBaseValue());
            Assert.assertTrue(changed.getAbilities(game).containsClass(HasteAbility.class));
            Assert.assertEquals("Grizzly Bears", original.getName());
        });
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void testBoostAppliesToCreatureFaceOfModalCard() {
        addCard(Zone.HAND, playerA, "Kazandu Mammoth");
        runCode("modal creature boost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Card card = player.getHand().getCards(game).iterator().next();
            PerpetualUtil.boost(card, 1, 1, card.getSpellAbility(), game);
            Assert.assertEquals(4, game.getCard(card.getId()).getPower().getBaseValue());
        });
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }
}
