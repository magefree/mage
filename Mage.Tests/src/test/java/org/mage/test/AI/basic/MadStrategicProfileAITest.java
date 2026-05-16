package org.mage.test.AI.basic;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.analysis.DeckProfile;
import mage.cards.decks.analysis.DeckProfileService;
import mage.cards.decks.analysis.DeckRole;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.player.ai.DeckStrategyProfile;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MadStrategicProfileAITest {

    @Test
    public void derivesWeightsFromCommanderAnchoredSacrificeProfile() {
        Deck mainDeck = new Deck();
        add(mainDeck, "Viscera Seer", 4);
        add(mainDeck, "Dragon Fodder", 4);
        add(mainDeck, "Reassembling Skeleton", 4);
        add(mainDeck, "Swamp", 12);
        add(mainDeck, "Mountain", 8);

        Deck commandZone = new Deck();
        add(commandZone, "Blood Artist", 1);

        DeckProfile mainProfile = DeckProfileService.analyze(mainDeck);
        DeckProfile commandProfile = DeckProfileService.analyze(commandZone);
        DeckStrategyProfile strategyProfile = DeckStrategyProfile.fromProfiles(mainProfile, commandProfile);

        Assert.assertTrue(strategyProfile.getRoleWeight(DeckRole.SACRIFICE_FODDER_PROVIDER)
                > strategyProfile.getRoleWeight(DeckRole.THREAT));
        Assert.assertTrue(strategyProfile.getRoleWeight(DeckRole.SACRIFICE_OUTLET)
                > strategyProfile.getRoleWeight(DeckRole.THREAT));
        Assert.assertTrue(strategyProfile.getRoleWeight(DeckRole.DEATH_PAYOFF)
                > strategyProfile.getRoleWeight(DeckRole.THREAT));
        Assert.assertTrue(strategyProfile.describeRole(DeckRole.DEATH_PAYOFF).contains("commander role"));
        Assert.assertTrue(strategyProfile.describeRole(DeckRole.SACRIFICE_FODDER_PROVIDER).contains("SACRIFICE"));
    }

    @Test
    public void keepsGenericThreatsLowerThanPackageRoles() {
        Deck mainDeck = new Deck();
        add(mainDeck, "Grizzly Bears", 4);
        add(mainDeck, "Divination", 4);
        add(mainDeck, "Murder", 4);
        add(mainDeck, "Forest", 10);
        add(mainDeck, "Island", 10);
        add(mainDeck, "Swamp", 4);

        DeckStrategyProfile strategyProfile = DeckStrategyProfile.fromProfiles(
                DeckProfileService.analyze(mainDeck),
                DeckProfileService.analyze(new Deck())
        );

        Assert.assertTrue(strategyProfile.getRoleWeight(DeckRole.CARD_DRAW)
                > strategyProfile.getRoleWeight(DeckRole.THREAT));
        Assert.assertTrue(strategyProfile.getRoleWeight(DeckRole.REMOVAL)
                > strategyProfile.getRoleWeight(DeckRole.THREAT));
    }

    @Test
    public void exposesFeatureSignalsForCommanderMatchedClusterCards() {
        Deck mainDeck = new Deck();
        add(mainDeck, "Dragon Fodder", 1);
        add(mainDeck, "Krenko's Command", 1);
        add(mainDeck, "Hordeling Outburst", 1);
        add(mainDeck, "Mogg War Marshal", 1);
        add(mainDeck, "Mountain", 20);

        Deck commandZone = new Deck();
        add(commandZone, "Young Pyromancer", 1);

        DeckStrategyProfile strategyProfile = DeckStrategyProfile.fromProfiles(
                DeckProfileService.analyze(mainDeck),
                DeckProfileService.analyze(commandZone)
        );

        List<DeckStrategyProfile.FeatureSignal> signals = strategyProfile.getFeatureSignalsForCard("Dragon Fodder");
        Assert.assertTrue(signals.stream().anyMatch(signal -> signal.getLabel().equals("profile:cluster")));
        Assert.assertTrue(signals.stream().anyMatch(signal -> signal.getLabel().equals("profile:commander-match")));
        Assert.assertTrue(signals.stream().allMatch(signal -> signal.getValue() > 0));
    }

    private static void add(Deck deck, String cardName, int amount) {
        CardInfo cardInfo = CardRepository.instance.findCard(cardName);
        Assert.assertNotNull("Missing test card: " + cardName, cardInfo);
        for (int i = 0; i < amount; i++) {
            Card card = cardInfo.createCard();
            Assert.assertNotNull("Could not create test card: " + cardName, card);
            deck.getCards().add(card);
        }
    }
}
