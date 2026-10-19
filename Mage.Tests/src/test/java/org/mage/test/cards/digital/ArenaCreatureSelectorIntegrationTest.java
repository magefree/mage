package org.mage.test.cards.digital;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.util.ArenaCreaturePool;
import mage.util.RandomCardUtil;
import mage.util.RandomUtil;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ArenaCreatureSelectorIntegrationTest extends CardTestPlayerBase {

    @Test
    public void testSelectionAndMissingManaValues() {
        runCode("random selection", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Card first = RandomCardUtil.createRandomCreature(13);
            Card second = RandomCardUtil.createRandomCreature(13);
            assertNotNull(first);
            assertNotNull(second);
            assertEquals("Emrakul, the Promised End", first.getName());
            assertEquals(13, first.getManaValue());
            assertNotEquals(first.getId(), second.getId());
            assertNull(RandomCardUtil.createRandomCreature(16));
            assertNull(RandomCardUtil.createRandomCreature(-1));
            assertNull(RandomCardUtil.createRandomCreature(10000));
        });
        finish(PhaseStep.POSTCOMBAT_MAIN);
    }

    @Test
    public void testAvailableCardsIncludeDigitalAndEventOnlyCreatures() {
        runCode("Arena availability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            ArenaCreaturePool catalog = ArenaCreaturePool.getInstance();
            Set<String> missing = RandomCardUtil.getUnimplementedArenaCreatureNames();
            assertFalse(missing.contains("Bringer of the Blue Dawn"));
            assertFalse(missing.contains("Emrakul, the Aeons Torn"));
            // Missing Arena cards are auditable, never replaced with paper-only cards.
            System.out.println("Arena creature coverage: " + (catalog.getAllCardNames().size() - missing.size())
                    + "/" + catalog.getAllCardNames().size());
            RandomUtil.setSeed(12345);
            for (int mv = 0; mv <= 16; mv++) {
                for (int i = 0; i < 32; i++) {
                    Card card = RandomCardUtil.createRandomCreature(mv);
                    if (card != null) {
                        assertEquals(mv, card.getManaValue());
                        if (catalog.getCanonicalName(card.getName(), mv) == null) {
                            Date release = Sets.findSet(card.getExpansionSetCode()).getArenaReleaseDate();
                            assertNotNull(card.getName(), release);
                            assertFalse(card.getName(), release.after(new Date()));
                        }
                        assertFalse(card.isNightCard());
                        assertFalse(card instanceof Token);
                    } else {
                        assertTrue(mv == 14 || mv == 16);
                    }
                }
            }
        });
        finish(PhaseStep.POSTCOMBAT_MAIN);
    }

    @Test
    public void testFullSetArenaLaunchMetadata() {
        assertEquals(ExpansionSet.buildDate(2023, 12, 12), Sets.findSet("KTK").getArenaReleaseDate());
        assertEquals(ExpansionSet.buildDate(2024, 6, 11), Sets.findSet("MH3").getArenaReleaseDate());
        assertEquals(ExpansionSet.buildDate(2023, 6, 20), Sets.findSet("LTR").getArenaReleaseDate());
        assertEquals(ExpansionSet.buildDate(2026, 6, 16), Sets.findSet("SPM").getArenaReleaseDate());
        assertNull(Sets.findSet("MH2").getArenaReleaseDate());
        assertNull(Sets.findSet("LCC").getArenaReleaseDate());
    }

    @Test
    public void testAllAuditedNameAndSetFilterRepairsAreSelectable() throws IOException {
        InputStream input = getClass().getResourceAsStream("/arena-creature-selector-repairs.tsv");
        assertNotNull(input);
        List<String[]> repairs;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            repairs = reader.lines().skip(1).map(line -> line.split("\t")).collect(Collectors.toList());
        }
        assertEquals(38, repairs.size());
        runCode("audited selector repairs", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            ArenaCreaturePool catalog = ArenaCreaturePool.getInstance();
            Set<String> missing = RandomCardUtil.getUnimplementedArenaCreatureNames();
            for (String[] repair : repairs) {
                String arenaName = repair[0];
                String xmageName = repair[1];
                int manaValue = Integer.parseInt(repair[2]);
                assertEquals(arenaName, catalog.getCanonicalName(xmageName, manaValue));
                assertFalse(arenaName, missing.contains(arenaName));
                Card card = CardRepository.instance.findCards(xmageName).stream()
                        .filter(candidate -> !candidate.isNightCard() && candidate.getManaValue() == manaValue)
                        .findFirst().get().createCard();
                assertNotNull(arenaName, card);
                assertTrue(arenaName, card.getCardType().contains(CardType.CREATURE));
                assertEquals(manaValue, card.getManaValue());
            }
        });
        finish(PhaseStep.POSTCOMBAT_MAIN);
    }

    @Test
    public void testMultiFacedAndAdventureCardsUseFrontIdentities() {
        runCode("front faces only", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            ArenaCreaturePool catalog = ArenaCreaturePool.getInstance();
            Set<String> missing = RandomCardUtil.getUnimplementedArenaCreatureNames();
            for (String name : new String[]{"Esika, God of the Tree", "Valki, God of Lies",
                    "Jorn, God of Winter", "Tangled Florahedron", "Bonecrusher Giant", "Delver of Secrets"}) {
                assertTrue(name, catalog.getAllCardNames().contains(name));
                assertFalse(name, missing.contains(name));
            }
            for (String name : new String[]{"The Prismatic Bridge", "Tibalt, Cosmic Impostor",
                    "Kaldring, the Rimestaff", "Tangled Vale", "Stomp", "Insectile Aberration",
                    "Ragnarok, Divine Deliverance"}) {
                assertFalse(name, catalog.getAllCardNames().contains(name));
            }
        });
        finish(PhaseStep.POSTCOMBAT_MAIN);
    }

    @Test
    public void testPoolSimulationUsesActualArenaPool() {
        addCard(Zone.BATTLEFIELD, playerA, "Pool of Vigorous Growth");
        runCode("same pool in simulation", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent pool = game.getBattlefield().getAllActivePermanents(player.getId()).stream()
                    .filter(permanent -> permanent.getName().equals("Pool of Vigorous Growth")).findFirst().get();
            Ability ability = pool.getAbilities(game).stream()
                    .filter(a -> a.getRule().contains("Create a token that's a copy")).findFirst().get().copy();
            Game simulation = game.createSimulationForAI();
            ability.setCostsTag("X", 16);
            assertFalse(ability.getEffects().get(0).apply(simulation, ability));
            assertEquals(1, simulation.getBattlefield().getAllActivePermanents(player.getId()).size());
            ability.setCostsTag("X", 13);
            assertTrue(ability.getEffects().get(0).apply(simulation, ability));
            Permanent token = simulation.getBattlefield().getAllActivePermanents(player.getId()).stream()
                    .filter(permanent -> permanent.getName().equals("Emrakul, the Promised End")).findFirst().get();
            assertTrue(token.isToken());
            assertEquals(13, token.getManaValue());
        });
        finish(PhaseStep.POSTCOMBAT_MAIN);
    }

    @Test
    public void testPoolProducesNothingForUnavailableManaValue() {
        addCard(Zone.BATTLEFIELD, playerA, "Pool of Vigorous Growth");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 16);
        addCard(Zone.HAND, playerA, "Island");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, {T}, Discard");
        setChoice(playerA, "X=16");
        setChoice(playerA, "Island");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertPermanentCount(playerA, "Draco", 0);
        assertEquals(17, currentGame.getBattlefield().getAllActivePermanents(playerA.getId()).size());
        assertGraveyardCount(playerA, "Island", 1);
    }

    @Test
    public void testPoolOfVigorousGrowthStillCreatesToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Pool of Vigorous Growth");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 16);
        addCard(Zone.HAND, playerA, "Island");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, {T}, Discard");
        setChoice(playerA, "X=13");
        setChoice(playerA, "Island");
        finish(PhaseStep.POSTCOMBAT_MAIN);
        assertTokenCount(playerA, "Emrakul, the Promised End", 1);
        assertGraveyardCount(playerA, "Island", 1);
    }

    private void finish(PhaseStep phaseStep) {
        setStrictChooseMode(true);
        setStopAt(1, phaseStep);
        execute();
    }
}
