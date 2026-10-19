package org.mage.test.cards.digital;

import mage.abilities.common.TurnFaceUpAbility;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.util.RandomCardUtil;
import mage.util.RandomUtil;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class RandomCreatureConjureTest extends CardTestPlayerBase {

    @Test
    public void testAninaProducesNothingForUnavailableManaValue() {
        runCode("Anina is selectable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) ->
                assertFalse(RandomCardUtil.getUnimplementedArenaCreatureNames().contains("Anina, Natural Parallelist")));
        addCard(Zone.BATTLEFIELD, playerA, "Anina, Natural Parallelist");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 16);
        addCard(Zone.HAND, playerA, "Endless One");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Endless One");
        setChoice(playerA, "X=16");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertPermanentCount(playerA, "Draco", 0);
        assertPermanentCount(playerA, "Endless One", 1);
        assertEquals(18, currentGame.getBattlefield().getAllActivePermanents(playerA.getId()).size());
    }

    @Test
    public void testCloneCrafterUsesLibraryEvenForPaperOnlyCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Clone Crafter");
        addCard(Zone.LIBRARY, playerB, "Draco");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone Crafter");
        setChoice(playerA, "PlayerB");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertHandCount(playerA, "Draco", 1);
        assertLibraryCount(playerB, "Draco", 1);
    }

    @Test
    public void testOrnateImitationsConjuresEachManaValueAsCards() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Torpor Orb");
        addCard(Zone.HAND, playerA, "Ornate Imitations");
        Set<UUID> existing = new HashSet<>();
        runCode("original cards", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            game.getCards().forEach(card -> existing.add(card.getId()));
            RandomUtil.setSeed(20);
        });
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ornate Imitations");
        setChoice(playerA, "X=2");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("two new cards", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            List<Card> created = game.getCards().stream().filter(card -> !existing.contains(card.getId()))
                    .filter(card -> card.getMainCard().getId().equals(card.getId()))
                    .collect(Collectors.toList());
            assertEquals(2, created.size());
            assertEquals(1, created.stream().filter(card -> card.getManaValue() == 1).count());
            assertEquals(1, created.stream().filter(card -> card.getManaValue() == 2).count());
            created.forEach(card -> assertEquals(playerA.getId(), card.getOwnerId()));
        });
        finish(PhaseStep.POSTCOMBAT_MAIN);
    }

    @Test
    public void testPositiveXMinimum() {
        addCard(Zone.HAND, playerA, "Ornate Imitations");
        addCard(Zone.HAND, playerA, "Expansive Reapplication");
        runCode("minimum X", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            for (Card card : player.getHand().getCards(game)) {
                assertTrue(CardUtil.castStream(card.getSpellAbility().getManaCosts(), VariableManaCost.class)
                        .allMatch(cost -> cost.getMinX() == 1));
            }
        });
        finish(PhaseStep.POSTCOMBAT_MAIN);
    }

    @Test
    public void testExpansiveReapplicationAndConjuredCardCanBeBounced() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 17);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 20);
        addCard(Zone.HAND, playerA, "Expansive Reapplication");
        addCard(Zone.HAND, playerA, "Evacuation");
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Expansive Reapplication", "Lightning Bolt", "Lightning Bolt");
        setChoice(playerA, "X=13");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("conjured", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emrakul, the Promised End", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Evacuation");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertHandCount(playerA, "Emrakul, the Promised End", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertLife(playerA, 20);
    }

    @Test
    public void testExpansiveReapplicationStillConjuresWhenSpellCannotBeCountered() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 17);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Expansive Reapplication");
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring");
        addCard(Zone.HAND, playerB, "Abrupt Decay");
        addCard(Zone.BATTLEFIELD, playerB, "Forest");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Abrupt Decay", "Sol Ring");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Expansive Reapplication", "Abrupt Decay", "Abrupt Decay");
        setChoice(playerA, "X=13");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertPermanentCount(playerA, "Emrakul, the Promised End", 1);
        assertGraveyardCount(playerA, "Sol Ring", 1);
    }

    @Test
    public void testAninaUsesSpellX() {
        addCard(Zone.BATTLEFIELD, playerA, "Anina, Natural Parallelist");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 16);
        addCard(Zone.HAND, playerA, "Endless One");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Endless One");
        setChoice(playerA, "X=13");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertPermanentCount(playerA, "Emrakul, the Promised End", 1);
        assertPermanentCount(playerA, "Endless One", 1);
    }

    @Test
    public void testAninaRemembersXAfterSpellIsCountered() {
        addCard(Zone.BATTLEFIELD, playerA, "Anina, Natural Parallelist");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 16);
        addCard(Zone.HAND, playerA, "Endless One");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Endless One");
        setChoice(playerA, "X=13");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Endless One", "Endless One");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertPermanentCount(playerA, "Emrakul, the Promised End", 1);
        assertGraveyardCount(playerA, "Endless One", 1);
    }

    @Test
    public void testAninaManaPaysForXSpells() {
        addCard(Zone.BATTLEFIELD, playerA, "Anina, Natural Parallelist");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 11);
        addCard(Zone.HAND, playerA, "Endless One");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Endless One");
        setChoice(playerA, "X=13");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertPermanentCount(playerA, "Endless One", 1);
        assertPowerToughness(playerA, "Endless One", 13, 13);
        assertTapped("Anina, Natural Parallelist", true);
    }

    @Test
    public void testAninaManaCannotPayForOrdinarySpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Anina, Natural Parallelist");
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        checkPlayableAbility("ordinary spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);
        finish(PhaseStep.POSTCOMBAT_MAIN);
    }

    @Test
    public void testFearOfChangeEntersAndDies() {
        addCard(Zone.BATTLEFIELD, playerA, "Autochthon Wurm", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Fear of Change");
        addCard(Zone.HAND, playerA, "Murder");
        // A 15-mana creature requests mana value 17, for which no card exists.
        // Both required exiles must still happen without a selectable result.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fear of Change");
        setChoice(playerA, "Autochthon Wurm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Fear of Change");
        setChoice(playerA, "Autochthon Wurm");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertExileCount(playerA, "Autochthon Wurm", 2);
        assertGraveyardCount(playerA, "Fear of Change", 1);
    }

    @Test
    public void testFearOfChangeCannotExileItself() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Fear of Change");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fear of Change");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertPermanentCount(playerA, "Fear of Change", 1);
    }

    @Test
    public void testFearOfChangeConjuresCreatureTwoManaHigher() {
        addCard(Zone.BATTLEFIELD, playerA, "Emrakul, the Promised End");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Fear of Change");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fear of Change");
        setChoice(playerA, "Emrakul, the Promised End");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("mana value fifteen", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            List<Permanent> created = game.getBattlefield().getAllActivePermanents(player.getId()).stream()
                    .filter(permanent -> permanent.getManaValue() == 15).collect(Collectors.toList());
            assertEquals(1, created.size());
            assertFalse(created.get(0).isToken());
        });
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertExileCount(playerA, "Emrakul, the Promised End", 1);
    }

    @Test
    public void testRoaleskCloaksAndGrantsCheapFaceUpAction() {
        addCard(Zone.HAND, playerA, "Roalesk, Prime Specimen");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 20);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Roalesk, Prime Specimen using Disguise", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}{U}: Turn this face-down permanent face up");
        setChoice(playerA, true);
        setChoiceAmount(playerA, 13);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("cloaked", 1, PhaseStep.PRECOMBAT_MAIN, playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 1);
        runCode("cloak properties", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent cloaked = game.getBattlefield().getAllActivePermanents(player.getId()).stream()
                    .filter(Permanent::isCloaked).findFirst().get();
            assertTrue(cloaked.isFaceDown(game));
            assertEquals(2, cloaked.getPower().getValue());
            assertTrue(cloaked.getAbilities(game).stream().anyMatch(ability -> ability instanceof TurnFaceUpAbility
                    && ability.getManaCosts().getText().equals("{G/U}")));
        });
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G/U}: Turn this face-down permanent face up");
        setChoice(playerA, false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("cheap action expired", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent conjured = game.getBattlefield().getAllActivePermanents(player.getId()).stream()
                    .filter(permanent -> permanent.getName().equals("Emrakul, the Promised End")).findFirst().get();
            assertFalse(conjured.isFaceDown(game));
            assertFalse(conjured.getAbilities(game).stream().anyMatch(ability -> ability instanceof TurnFaceUpAbility));
        });
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertPermanentCount(playerA, "Emrakul, the Promised End", 1);
        assertHandCount(playerA, "Emrakul, the Promised End", 0);
    }

    @Test
    public void testRoaleskZeroDoesNotConjure() {
        addCard(Zone.HAND, playerA, "Roalesk, Prime Specimen");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Roalesk, Prime Specimen using Disguise", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}{U}: Turn this face-down permanent face up");
        setChoice(playerA, true);
        setChoiceAmount(playerA, 0);
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 0);
        assertPermanentCount(playerA, "Roalesk, Prime Specimen", 1);
    }

    @Test
    public void testDiabolicFormulaUsesGreatestRelevantCardInGroup() {
        addCard(Zone.BATTLEFIELD, playerA, "Diabolic Formula");
        addCard(Zone.BATTLEFIELD, playerA, "Torpor Orb");
        addCard(Zone.GRAVEYARD, playerA, "Emrakul, the Promised End");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Strike");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");
        addCard(Zone.GRAVEYARD, playerB, "Enter the Infinite");
        Set<UUID> existing = new HashSet<>();
        runCode("exile together", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            game.getCards().forEach(card -> existing.add(card.getId()));
            Permanent formula = game.getBattlefield().getAllActivePermanents(player.getId()).stream()
                    .filter(permanent -> permanent.getName().equals("Diabolic Formula")).findFirst().get();
            Set<Card> cards = new HashSet<>(player.getGraveyard().getCards(game));
            cards.addAll(playerB.getGraveyard().getCards(game));
            RandomUtil.setSeed(20);
            player.moveCards(cards, Zone.EXILED, formula.getSpellAbility(), game);
        });
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("one two-mana result", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            List<Card> created = game.getCards().stream().filter(card -> !existing.contains(card.getId()))
                    .filter(card -> card.getMainCard().getId().equals(card.getId()))
                    .collect(Collectors.toList());
            assertEquals(1, created.size());
            assertEquals(2, created.get(0).getManaValue());
        });
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertExileCount(playerA, "Emrakul, the Promised End", 1);
    }

    @Test
    public void testCloneCrafterPreservesOriginalAndPrismaticAcrossZoneChanges() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Clone Crafter");
        addCard(Zone.HAND, playerA, "Unsummon");
        addCard(Zone.LIBRARY, playerB, "Savannah Lions", 3);
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone Crafter");
        setChoice(playerA, "PlayerB");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Savannah Lions", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unsummon", "Savannah Lions", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Savannah Lions");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertPermanentCount(playerA, "Savannah Lions", 1);
        assertLibraryCount(playerB, "Savannah Lions", 3);
        assertTokenCount(playerA, "Savannah Lions", 0);
    }

    @Test
    public void testCloneCrafterWithNoCreatureInLibrary() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Clone Crafter");
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone Crafter");
        setChoice(playerA, "PlayerB");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertHandCount(playerA, 0);
        assertLibraryCount(playerB, "Lightning Bolt", 3);
    }

    @Test
    public void testPrismaticDoesNotPayForActivatedAbilities() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 9);
        addCard(Zone.HAND, playerA, "Clone Crafter");
        addCard(Zone.LIBRARY, playerB, "Shivan Dragon");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone Crafter");
        setChoice(playerA, "PlayerB");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shivan Dragon", true);
        checkPlayableAbility("red activation still needs red", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{R}: Shivan Dragon gets +1/+0", false);
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertPermanentCount(playerA, "Shivan Dragon", 1);
    }

    @Test
    public void testCloneCrafterCopiesAdventureCardAndAllItsSpellsGainPrismatic() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerA, "Clone Crafter");
        addCard(Zone.LIBRARY, playerB, "Bonecrusher Giant");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone Crafter");
        setChoice(playerA, "PlayerB");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stomp", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bonecrusher Giant");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertPermanentCount(playerA, "Bonecrusher Giant", 1);
        assertLibraryCount(playerB, "Bonecrusher Giant", 1);
        assertLife(playerB, 18);
    }

    @Test
    public void testCosmicSovereignUsesPowerAndExilesResult() {
        addCard(Zone.BATTLEFIELD, playerA, "Cosmic Sovereign");
        runCode("power thirteen", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent sovereign = game.getBattlefield().getAllActivePermanents(player.getId()).stream()
                    .filter(permanent -> permanent.getName().equals("Cosmic Sovereign")).findFirst().get();
            game.addEffect(new BoostTargetEffect(10, 0, Duration.EndOfTurn)
                    .setTargetPointer(new FixedTarget(sovereign, game)), sovereign.getSpellAbility());
        });
        checkPermanentCount("combat conjure", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Emrakul, the Promised End", 1);
        finish(PhaseStep.END_TURN);
        assertExileCount(playerA, "Emrakul, the Promised End", 1);
        assertPermanentCount(playerA, "Emrakul, the Promised End", 0);
    }

    @Test
    public void testCosmicSovereignTracksConjuredModalDoubleFacedPermanent() {
        addCard(Zone.BATTLEFIELD, playerA, "Cosmic Sovereign");
        runCode("power two", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent sovereign = game.getBattlefield().getAllActivePermanents(player.getId()).stream()
                    .filter(permanent -> permanent.getName().equals("Cosmic Sovereign")).findFirst().get();
            game.addEffect(new BoostTargetEffect(-1, 0, Duration.EndOfTurn)
                    .setTargetPointer(new FixedTarget(sovereign, game)), sovereign.getSpellAbility());
        });
        runCode("select MDFC front", 1, PhaseStep.BEGIN_COMBAT, playerA, (info, player, game) -> {
            int selectedSeed = -1;
            for (int seed = 0; seed < 50000; seed++) {
                RandomUtil.setSeed(seed);
                if (RandomCardUtil.createRandomCreature(2).getName().equals("Tangled Florahedron")) {
                    selectedSeed = seed;
                    break;
                }
            }
            assertTrue("MDFC must be selectable", selectedSeed >= 0);
            RandomUtil.setSeed(selectedSeed);
        });
        runCode("MDFC has haste", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent conjured = game.getBattlefield().getAllActivePermanents(player.getId()).stream()
                    .filter(permanent -> permanent.getName().equals("Tangled Florahedron")).findFirst().get();
            assertFalse(conjured.isToken());
            assertTrue(conjured.hasAbility(HasteAbility.getInstance(), game));
            assertEquals(2, conjured.getManaValue());
        });
        finish(PhaseStep.END_TURN);
        assertExileCount(playerA, "Tangled Florahedron", 1);
        assertPermanentCount(playerA, "Tangled Florahedron", 0);
    }

    @Test
    public void testCosmicSovereignDoesNotExileReturnedCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Cosmic Sovereign");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Flicker");
        runCode("power thirteen", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent sovereign = game.getBattlefield().getAllActivePermanents(player.getId()).stream()
                    .filter(permanent -> permanent.getName().equals("Cosmic Sovereign")).findFirst().get();
            game.addEffect(new BoostTargetEffect(10, 0, Duration.EndOfTurn)
                    .setTargetPointer(new FixedTarget(sovereign, game)), sovereign.getSpellAbility());
        });
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flicker", "Emrakul, the Promised End", true);
        finish(PhaseStep.END_TURN);
        assertPermanentCount(playerA, "Emrakul, the Promised End", 1);
        assertExileCount(playerA, "Emrakul, the Promised End", 0);
    }

    @Test
    public void testVariableSolutionsOneSeeksBasicLandTapped() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.LIBRARY, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Variable Solutions");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Variable Solutions");
        setChoice(playerA, "X=1");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertEquals(3, currentGame.getBattlefield().getAllActivePermanents(playerA.getId()).stream()
                .filter(permanent -> permanent.isLand(currentGame)).count());
        assertTapped("Island", true);
        assertLife(playerA, 20);
    }

    @Test
    public void testVariableSolutionsTwoOnlySacrificesOpponentArtifact() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring");
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring");
        addCard(Zone.HAND, playerA, "Variable Solutions");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Variable Solutions");
        setChoice(playerA, "X=2");
        setChoice(playerB, "Sol Ring");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertPermanentCount(playerA, "Sol Ring", 1);
        assertGraveyardCount(playerB, "Sol Ring", 1);
        assertLife(playerA, 20);
    }

    @Test
    public void testVariableSolutionsLargeXDoesEveryEffectUsingActualX() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 17);
        addCard(Zone.LIBRARY, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring");
        addCard(Zone.HAND, playerA, "Variable Solutions");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Variable Solutions");
        setChoice(playerA, "X=13");
        setChoice(playerB, "Sol Ring");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertPermanentCount(playerA, "Emrakul, the Promised End", 1);
        assertEquals(18, currentGame.getBattlefield().getAllActivePermanents(playerA.getId()).stream()
                .filter(permanent -> permanent.isLand(currentGame)).count());
        assertTapped("Island", true);
        assertGraveyardCount(playerB, "Sol Ring", 1);
        assertLife(playerA, 22);
    }

    private void finish(PhaseStep phaseStep) {
        setStrictChooseMode(true);
        setStopAt(1, phaseStep);
        execute();
    }
}
