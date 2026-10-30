package org.mage.test.serverside.tournament;

import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.CardScanner;
import mage.constants.RangeOfInfluence;
import mage.game.draft.*;
import mage.players.StubPlayer;
import mage.server.draft.DraftPickSuggesters;
import mage.server.draft.RatingDraftPickSuggester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Draft: suggested picks for human players (icons in GUI and autopick on timeout)
 *
 * @author JayDi85
 */
public class DraftPickSuggestTest {

    @Before
    public void setUp() {
        CardScanner.scan();
    }

    @Test
    public void test_Ratings_Top3ByRatingsWithoutColors() {
        // FRA ratings: Mindseeker Oculus 46, Extended Absence 44, Last Gasp 35, No Admittance 35, Hexhaven Battalion 34
        List<Card> booster = createBooster();
        List<DraftPickSuggest> suggests = new RatingDraftPickSuggester().suggest(booster, Collections.emptyList());

        Assert.assertEquals(Arrays.asList("Mindseeker Oculus", "Extended Absence", "Last Gasp"), names(suggests, booster));
        Assert.assertTrue(suggests.get(0).getInfo(), suggests.get(0).getInfo().contains("17Lands FRA"));
        Assert.assertTrue(suggests.get(0).getInfo(), suggests.get(0).getInfo().contains("deck colors not chosen yet"));
    }

    @Test
    public void test_Ratings_MustUseDeckColors() {
        // picks: W + R cards, so off-color cards must go last
        List<Card> booster = createBooster();
        List<Card> picks = new ArrayList<>();
        addCards(picks, "Hexhaven Battalion", 3); // W
        addCards(picks, "No Admittance", 3); // R
        List<DraftPickSuggest> suggests = new RatingDraftPickSuggester().suggest(booster, picks);

        Assert.assertEquals(Arrays.asList("No Admittance", "Hexhaven Battalion", "Mindseeker Oculus"), names(suggests, booster));
        Assert.assertTrue(suggests.get(0).getInfo(), suggests.get(0).getInfo().contains("deck colors WR"));
        Assert.assertFalse(suggests.get(0).getInfo(), suggests.get(0).getInfo().contains("off-color"));
        Assert.assertTrue(suggests.get(2).getInfo(), suggests.get(2).getInfo().contains("off-color"));
    }

    @Test
    public void test_Suggesters_AllSourcesInOrderWithRanks() {
        List<Card> booster = createBooster();
        List<DraftPickSuggester> sources = Arrays.asList(
                new TestSuggester("first", (b, p) -> Arrays.asList(
                        new DraftPickSuggest(b.get(4).getId(), "a"),
                        new DraftPickSuggest(b.get(3).getId(), "b")
                )),
                new TestSuggester("second", (b, p) -> Collections.singletonList(
                        new DraftPickSuggest(b.get(4).getId(), "c")
                ))
        );
        List<DraftPickSuggest> suggests = DraftPickSuggesters.makeSuggests(booster, Collections.emptyList(), sources, 5000);

        Assert.assertEquals(3, suggests.size());
        Assert.assertEquals("first #1: a", suggests.get(0).toString());
        Assert.assertEquals("first #2: b", suggests.get(1).toString());
        Assert.assertEquals("second #1: c", suggests.get(2).toString());
        Assert.assertEquals(booster.get(4).getId(), suggests.get(0).getCardId());
        Assert.assertEquals(booster.get(4).getId(), suggests.get(2).getCardId());
    }

    @Test
    public void test_Suggesters_MustIgnoreSlowAndBrokenSourcesAndKeepDraftData() {
        List<Card> booster = createBooster();
        List<Card> picks = new ArrayList<>();
        addCards(picks, "Last Gasp", 2);
        List<UUID> boosterIds = booster.stream().map(Card::getId).collect(Collectors.toList());
        List<String> boosterNames = booster.stream().map(Card::getName).collect(Collectors.toList());

        List<DraftPickSuggester> sources = Arrays.asList(
                new TestSuggester("slow", (b, p) -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ignore) {
                    }
                    return Collections.singletonList(new DraftPickSuggest(b.get(0).getId(), "slow"));
                }),
                new TestSuggester("error", (b, p) -> {
                    throw new IllegalStateException("test error");
                }),
                new TestSuggester("changer", (b, p) -> {
                    // source gets copies, so it can't change the draft
                    b.get(0).setName("changed name");
                    Card fakeCard = b.remove(1);
                    p.clear();
                    return Arrays.asList(
                            new DraftPickSuggest(UUID.randomUUID(), "unknown card"),
                            new DraftPickSuggest(fakeCard.getId(), "removed card"),
                            null
                    );
                })
        );
        long start = System.currentTimeMillis();
        List<DraftPickSuggest> suggests = DraftPickSuggesters.makeSuggests(booster, picks, sources, 500);
        long time = System.currentTimeMillis() - start;

        Assert.assertTrue("slow source must be skipped by timeout, but waited " + time + " ms", time < 3000);
        Assert.assertEquals("only real booster's cards must be used", 1, suggests.size());
        Assert.assertEquals("changer #1: removed card", suggests.get(0).toString());
        Assert.assertEquals("booster must be same", boosterIds, booster.stream().map(Card::getId).collect(Collectors.toList()));
        Assert.assertEquals("booster must be same", boosterNames, booster.stream().map(Card::getName).collect(Collectors.toList()));
        Assert.assertEquals("picks must be same", 2, picks.size());
    }

    @Test
    public void test_AutoPick_MustUseMarkedThenSuggestedThenLastCard() {
        // marked card
        Card picked = autoPick(true, true);
        Assert.assertEquals("marked card has priority", "Hexhaven Battalion", picked.getName());

        // suggested card
        picked = autoPick(false, true);
        Assert.assertEquals("first suggested card", "Extended Absence", picked.getName());

        // no suggestions
        picked = autoPick(false, false);
        Assert.assertEquals("last card", "Extended Absence", picked.getName()); // last card of the booster

        // outdated suggestions (cards from another booster) must be ignored
        BoosterDraft draft = new BoosterDraft(new DraftOptions(), Collections.emptyList());
        StubPlayer player = new StubPlayer("test", RangeOfInfluence.ALL);
        draft.addPlayer(player);
        DraftPlayer draftPlayer = draft.getPlayer(player.getId());
        draftPlayer.setBoosterAndLoad(createBooster());
        draftPlayer.setPickingAndSending();
        draft.setPickSuggests(player.getId(), Collections.singletonList(new DraftPickSuggest(UUID.randomUUID(), "outdated")));
        Assert.assertEquals("outdated suggestions must be ignored", 0, draftPlayer.getPickSuggests().size());
        Assert.assertFalse("suggestions are calculated", draft.isPickSuggestsNeeded(player.getId()));
    }

    private Card autoPick(boolean useMarked, boolean useSuggests) {
        BoosterDraft draft = new BoosterDraft(new DraftOptions(), Collections.emptyList());
        StubPlayer player = new StubPlayer("test", RangeOfInfluence.ALL);
        draft.addPlayer(player);
        DraftPlayer draftPlayer = draft.getPlayer(player.getId());
        List<Card> booster = createBooster();
        draftPlayer.setBoosterAndLoad(booster);
        draftPlayer.setPickingAndSending();
        Assert.assertTrue(draft.isPickSuggestsNeeded(player.getId()));
        if (useMarked) {
            draft.setMarkedCard(player.getId(), booster.get(0).getId()); // Hexhaven Battalion
        }
        if (useSuggests) {
            draft.setPickSuggests(player.getId(), Arrays.asList(
                    new DraftPickSuggest(booster.get(4).getId(), "first"), // Extended Absence
                    new DraftPickSuggest(booster.get(2).getId(), "second")
            ));
        }
        draft.autoPick(player.getId());
        Assert.assertFalse("must pick", draftPlayer.isPicking());
        Assert.assertEquals(1, draftPlayer.getDeck().getSideboard().size());
        return draftPlayer.getDeck().getSideboard().iterator().next();
    }

    private List<Card> createBooster() {
        List<Card> booster = new ArrayList<>();
        addCards(booster, "Hexhaven Battalion", 1); // W, 34
        addCards(booster, "Last Gasp", 1); // B, 35
        addCards(booster, "Mindseeker Oculus", 1); // U, 46
        addCards(booster, "No Admittance", 1); // R, 35
        addCards(booster, "Extended Absence", 1); // B, 44
        return booster;
    }

    private void addCards(List<Card> cards, String cardName, int amount) {
        CardInfo cardInfo = CardRepository.instance.findCards(cardName).stream()
                .filter(info -> info.getSetCode().equals("FRA"))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull("can't find card " + cardName, cardInfo);
        for (int i = 0; i < amount; i++) {
            cards.add(cardInfo.createCard());
        }
    }

    private List<String> names(List<DraftPickSuggest> suggests, List<Card> booster) {
        Map<UUID, String> names = booster.stream().collect(Collectors.toMap(Card::getId, Card::getName));
        return suggests.stream().map(s -> names.get(s.getCardId())).collect(Collectors.toList());
    }

    private static class TestSuggester implements DraftPickSuggester {

        private final String name;
        private final java.util.function.BiFunction<List<Card>, List<Card>, List<DraftPickSuggest>> logic;

        TestSuggester(String name, java.util.function.BiFunction<List<Card>, List<Card>, List<DraftPickSuggest>> logic) {
            this.name = name;
            this.logic = logic;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<DraftPickSuggest> suggest(List<Card> booster, List<Card> picks) {
            return logic.apply(booster, picks);
        }
    }
}
