package org.mage.test.decks.analysis;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.analysis.DeckProfile;
import mage.cards.decks.analysis.DeckProfileService;
import mage.cards.decks.analysis.DeckRole;
import mage.cards.decks.analysis.DeckSynergy;
import mage.cards.decks.analysis.DeckSynergyPackage;
import mage.cards.decks.analysis.DeckSynergyPackageService;
import mage.cards.decks.analysis.DeckProfileTextFormatter;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class DeckProfileServiceTest {

    @Test
    public void profileCapturesManaCurveAndCardTypes() {
        Deck deck = new Deck();
        add(deck, "Grizzly Bears", 2);
        add(deck, "Divination", 1);
        add(deck, "Murder", 1);
        add(deck, "Forest", 6);
        add(deck, "Island", 4);
        add(deck, "Swamp", 4);

        DeckProfile profile = DeckProfileService.analyze(deck);

        Assert.assertEquals(18, profile.getCardCount());
        Assert.assertEquals(14, profile.getLandCount());
        Assert.assertEquals(4, profile.getNonLandCount());
        Assert.assertEquals(2, (int) profile.getManaCurve().get(2));
        Assert.assertEquals(2, (int) profile.getManaCurve().get(3));
        Assert.assertEquals(2, (int) profile.getCardTypeCounts().get(CardType.CREATURE));
        Assert.assertEquals(1, (int) profile.getCardTypeCounts().get(CardType.SORCERY));
        Assert.assertEquals(1, (int) profile.getCardTypeCounts().get(CardType.INSTANT));
        Assert.assertTrue(profile.getColorPips().get(ColoredManaSymbol.G) > 0);
        Assert.assertTrue(profile.getColorPips().get(ColoredManaSymbol.U) > 0);
        Assert.assertTrue(profile.getColorPips().get(ColoredManaSymbol.B) > 0);
        Assert.assertTrue(profile.getRoleCount(DeckRole.CARD_DRAW) > 0);
        Assert.assertTrue(profile.getRoleCount(DeckRole.REMOVAL) > 0);
        Assert.assertEquals(0, profile.getRoleCount(DeckRole.RAMP));
        Assert.assertTrue(profile.getFeatureCount("type:creature") > 0);
        Assert.assertTrue(profile.getFeatureCount("type:instant") > 0);
        Assert.assertTrue(profile.getFeatureCount("outcome:drawcard") > 0);
        Assert.assertTrue(profile.getFeatureCardCounts("outcome:drawcard").containsKey("Divination"));
    }

    @Test
    public void profileInfersSacrificePlanFromRoleCombination() {
        Deck deck = new Deck();
        add(deck, "Viscera Seer", 4);
        add(deck, "Blood Artist", 4);
        add(deck, "Dragon Fodder", 4);
        add(deck, "Swamp", 12);
        add(deck, "Mountain", 8);

        DeckProfile profile = DeckProfileService.analyze(deck);

        Assert.assertTrue(profile.getRoleCount(DeckRole.SACRIFICE_OUTLET) >= 4);
        Assert.assertTrue(profile.getRoleCount(DeckRole.SACRIFICE_FODDER_PROVIDER) >= 4);
        Assert.assertTrue(profile.getRoleCount(DeckRole.DEATH_PAYOFF) >= 4);
        Assert.assertEquals(4, (int) profile.getRoleCardCounts(DeckRole.SACRIFICE_OUTLET).get("Viscera Seer"));
        Assert.assertEquals(4, (int) profile.getRoleCardCounts(DeckRole.DEATH_PAYOFF).get("Blood Artist"));
        Assert.assertEquals(4, (int) profile.getRoleCardCounts(DeckRole.TOKEN_MAKER).get("Dragon Fodder"));
        Assert.assertEquals(4, (int) profile.getRoleCardCounts(DeckRole.SACRIFICE_FODDER_PROVIDER).get("Dragon Fodder"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.SACRIFICE_FODDER).containsKey("Viscera Seer"));
        Assert.assertTrue(profile.getSynergyScore(DeckSynergy.SACRIFICE) > 0.0);
    }

    @Test
    public void synergyPackagesIncludeCommanderAnchorsAndSupportingCards() {
        Deck mainDeck = new Deck();
        add(mainDeck, "Viscera Seer", 4);
        add(mainDeck, "Dragon Fodder", 4);
        add(mainDeck, "Swamp", 12);
        add(mainDeck, "Mountain", 8);

        Deck commandZone = new Deck();
        add(commandZone, "Blood Artist", 1);

        DeckProfile mainProfile = DeckProfileService.analyze(mainDeck);
        DeckProfile commandProfile = DeckProfileService.analyze(commandZone);
        Map<DeckSynergy, DeckSynergyPackage> packages = DeckSynergyPackageService.buildPackages(mainProfile, commandProfile);
        DeckSynergyPackage sacrifice = packages.get(DeckSynergy.SACRIFICE);

        Assert.assertTrue(sacrifice.isCommanderAnchored());
        Assert.assertTrue(sacrifice.getPresentComponentCount() >= 3);
        Assert.assertEquals(4, (int) sacrifice.getMainDeckCards(DeckRole.SACRIFICE_OUTLET).get("Viscera Seer"));
        Assert.assertEquals(1, (int) sacrifice.getCommandZoneCards(DeckRole.DEATH_PAYOFF).get("Blood Artist"));

        String report = DeckProfileTextFormatter.formatCommanderDeck(mainProfile, commandProfile);
        Assert.assertTrue(report.contains("Commander anchors"));
        Assert.assertTrue(report.contains("Commander feature matches"));
        Assert.assertTrue(report.contains("SACRIFICE coverage"));
        Assert.assertTrue(report.contains("Viscera Seer"));
        Assert.assertTrue(report.contains("Blood Artist"));
    }

    @Test
    public void xSpellCommanderCreatesCommanderAnchoredPackages() {
        Deck mainDeck = new Deck();
        add(mainDeck, "Hydroid Krasis", 1);
        add(mainDeck, "Stonecoil Serpent", 1);
        add(mainDeck, "Forest", 8);
        add(mainDeck, "Island", 8);

        Deck commandZone = new Deck();
        add(commandZone, "Zimone, Infinite Analyst", 1);

        DeckProfile mainProfile = DeckProfileService.analyze(mainDeck);
        DeckProfile commandProfile = DeckProfileService.analyze(commandZone);
        Map<DeckSynergy, DeckSynergyPackage> packages = DeckSynergyPackageService.buildPackages(mainProfile, commandProfile);

        Assert.assertEquals(2, mainProfile.getRoleCount(DeckRole.X_SPELL));
        Assert.assertEquals(1, commandProfile.getRoleCount(DeckRole.X_SPELL_PAYOFF));
        Assert.assertEquals(1, commandProfile.getRoleCount(DeckRole.PLUS_ONE_COUNTER_PAYOFF));
        Assert.assertTrue(packages.get(DeckSynergy.X_SPELLS).isCommanderAnchored());
        Assert.assertTrue(packages.get(DeckSynergy.PLUS_ONE_COUNTERS).isCommanderAnchored());
        Assert.assertTrue(packages.get(DeckSynergy.X_SPELLS).getMainDeckCards(DeckRole.X_SPELL).containsKey("Hydroid Krasis"));
        Assert.assertTrue(packages.get(DeckSynergy.X_SPELLS).getCommandZoneCards(DeckRole.X_SPELL_PAYOFF).containsKey("Zimone, Infinite Analyst"));
    }

    @Test
    public void selfSacrificeFixingLandsDoNotCountAsSacrificeOutlets() {
        Deck deck = new Deck();
        add(deck, "Terramorphic Expanse", 1);
        add(deck, "Fabled Passage", 1);
        add(deck, "High Market", 1);
        add(deck, "Scavenger Grounds", 1);
        add(deck, "Forest", 5);
        add(deck, "Swamp", 5);

        DeckProfile profile = DeckProfileService.analyze(deck);

        Assert.assertTrue(profile.getRoleCardCounts(DeckRole.MANA_FIXING).containsKey("Terramorphic Expanse"));
        Assert.assertTrue(profile.getRoleCardCounts(DeckRole.MANA_FIXING).containsKey("Fabled Passage"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.SACRIFICE_OUTLET).containsKey("Terramorphic Expanse"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.SACRIFICE_OUTLET).containsKey("Fabled Passage"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.SACRIFICE_OUTLET).containsKey("Scavenger Grounds"));
        Assert.assertTrue(profile.getRoleCardCounts(DeckRole.SACRIFICE_OUTLET).containsKey("High Market"));
    }

    @Test
    public void generatedRolesPreserveFalsePositiveGuards() {
        Deck deck = new Deck();
        add(deck, "Fabled Passage", 1);
        add(deck, "Viscera Seer", 1);
        add(deck, "Swamp", 4);

        DeckProfile profile = DeckProfileService.analyze(deck);

        Assert.assertTrue(profile.getRoleCardCounts(DeckRole.MANA_FIXING).containsKey("Fabled Passage"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.SACRIFICE_OUTLET).containsKey("Fabled Passage"));
        Assert.assertFalse(profile.getMechanicCardCounts("SACRIFICE").containsKey("Fabled Passage"));
        Assert.assertTrue(profile.getRoleCardCounts(DeckRole.SACRIFICE_OUTLET).containsKey("Viscera Seer"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.SACRIFICE_FODDER).containsKey("Viscera Seer"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.CARD_DRAW).containsKey("Viscera Seer"));
    }

    @Test
    public void profileSeparatesMechanicTagsFromRoles() {
        Deck deck = new Deck();
        add(deck, "Dragonlord's Servant", 1);
        add(deck, "Muscle Sliver", 1);
        add(deck, "Crux of Fate", 1);
        add(deck, "Mountain", 6);
        add(deck, "Swamp", 6);

        DeckProfile profile = DeckProfileService.analyze(deck);

        Assert.assertTrue(profile.getMechanicCardCounts("TRIBAL:Dragon").containsKey("Dragonlord's Servant"));
        Assert.assertTrue(profile.getMechanicCardCounts("TRIBAL:Dragon").containsKey("Crux of Fate"));
        Assert.assertTrue(profile.getMechanicCardCounts("SUBTYPE:Sliver").containsKey("Muscle Sliver"));
        Assert.assertTrue(profile.getMechanicCardCounts("TRIBAL:Sliver").containsKey("Muscle Sliver"));
        Assert.assertTrue(profile.getRoleCardCounts(DeckRole.BOARD_WIPE).containsKey("Crux of Fate"));
        Assert.assertFalse("Tribal terms should stay out of role names",
                profile.getRoleCounts().keySet().stream().anyMatch(role -> role.name().contains("DRAGON") || role.name().contains("SLIVER")));
    }

    @Test
    public void profileAvoidsBroadSacrificeAndPutCreatureNoise() {
        Deck deck = new Deck();
        add(deck, "Will of the Abzan", 1);
        add(deck, "Bloodghast", 1);
        add(deck, "Wall of Omens", 1);
        add(deck, "Hydroid Krasis", 1);
        add(deck, "Forest", 4);
        add(deck, "Island", 4);
        add(deck, "Swamp", 4);

        DeckProfile profile = DeckProfileService.analyze(deck);

        Assert.assertTrue(profile.getRoleCardCounts(DeckRole.REMOVAL).containsKey("Will of the Abzan"));
        Assert.assertTrue(profile.getRoleCardCounts(DeckRole.GRAVEYARD_RECURSION).containsKey("Will of the Abzan"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.SACRIFICE_OUTLET).containsKey("Will of the Abzan"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.SACRIFICE_FODDER).containsKey("Will of the Abzan"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.TOKEN_MAKER).containsKey("Will of the Abzan"));

        Assert.assertTrue(profile.getRoleCardCounts(DeckRole.GRAVEYARD_RECURSION).containsKey("Bloodghast"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.TOKEN_MAKER).containsKey("Bloodghast"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.LARGE_THREAT).containsKey("Wall of Omens"));
        Assert.assertTrue(profile.getRoleCardCounts(DeckRole.CARD_DRAW).containsKey("Wall of Omens"));
        Assert.assertTrue(profile.getRoleCardCounts(DeckRole.X_SPELL).containsKey("Hydroid Krasis"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.CHEAP_THREAT).containsKey("Hydroid Krasis"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.SACRIFICE_FODDER).containsKey("Hydroid Krasis"));
    }

    @Test
    public void cheapUtilityCreaturesAreNotAutomaticallySacrificeFodder() {
        Deck deck = new Deck();
        add(deck, "Millikin", 1);
        add(deck, "Sakura-Tribe Elder", 1);
        add(deck, "Dragon Fodder", 1);
        add(deck, "Forest", 8);

        DeckProfile profile = DeckProfileService.analyze(deck);

        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.SACRIFICE_FODDER).containsKey("Millikin"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.SACRIFICE_FODDER).containsKey("Sakura-Tribe Elder"));
        Assert.assertTrue(profile.getRoleCardCounts(DeckRole.SACRIFICE_FODDER_PROVIDER).containsKey("Dragon Fodder"));
        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.SACRIFICE_FODDER).containsKey("Dragon Fodder"));
    }

    @Test
    public void removingProtectiveAbilitiesDoesNotCountAsProtection() {
        Deck deck = new Deck();
        add(deck, "Arcane Lighthouse", 1);
        add(deck, "Heroic Intervention", 1);
        add(deck, "Forest", 8);

        DeckProfile profile = DeckProfileService.analyze(deck);

        Assert.assertFalse(profile.getRoleCardCounts(DeckRole.COUNTER_OR_PROTECTION).containsKey("Arcane Lighthouse"));
        Assert.assertTrue(profile.getRoleCardCounts(DeckRole.COUNTER_OR_PROTECTION).containsKey("Heroic Intervention"));
    }

    @Test
    public void profileExposesGenericCardFeatureSignals() {
        Deck deck = new Deck();
        add(deck, "Dragon Fodder", 2);
        add(deck, "Krenko's Command", 1);
        add(deck, "Raise the Alarm", 1);
        add(deck, "Murder", 1);
        add(deck, "Counterspell", 1);
        add(deck, "Divination", 1);
        add(deck, "Mountain", 4);
        add(deck, "Swamp", 4);

        DeckProfile profile = DeckProfileService.analyze(deck);

        Assert.assertTrue(profile.getFeatureCount("verb:create") > 0);
        Assert.assertTrue(profile.getFeatureCount("object:token") > 0);
        Assert.assertTrue(profile.getFeatureCount("verb:counter") > 0);
        Assert.assertTrue(profile.getFeatureCount("outcome:destroypermanent") > 0);
        Assert.assertTrue(profile.getFeatureCount("outcome:drawcard") > 0);
        Assert.assertTrue(profile.getFeatureCardCounts("verb:create").containsKey("Dragon Fodder"));
        Assert.assertTrue(profile.getFeatureCardCounts("verb:counter").containsKey("Counterspell"));
        Assert.assertTrue(profile.getFeatureCardCounts("outcome:destroypermanent").containsKey("Murder"));

        String report = DeckProfileTextFormatter.format("Feature deck", profile);
        Assert.assertTrue(report.contains("Top features"));
        Assert.assertTrue(report.contains("Feature clusters"));
        Assert.assertTrue(report.contains("Nearest feature matches"));
        Assert.assertTrue(report.contains("verb:create"));
        Assert.assertTrue(report.contains("Dragon Fodder"));
        Assert.assertTrue(report.contains("Krenko's Command"));
    }

    @Test
    public void genericFeatureTextMatchingAvoidsSubstringNoise() {
        Deck deck = new Deck();
        add(deck, "Stonecoil Serpent", 1);
        add(deck, "Rampant Growth", 1);
        add(deck, "Voltaic Key", 1);
        add(deck, "Forest", 8);

        DeckProfile profile = DeckProfileService.analyze(deck);

        Assert.assertTrue(profile.getFeatureCount("object:counter") > 0);
        Assert.assertTrue(profile.getFeatureCount("counter:+1/+1") > 0);
        Assert.assertTrue(profile.getFeatureCount("verb:untap") > 0);
        Assert.assertTrue(profile.getFeatureCount("keyword:reach") > 0);
        Assert.assertEquals(0, profile.getFeatureCount("verb:counter"));
        Assert.assertFalse(profile.getFeatureCardCounts("keyword:reach").containsKey("Rampant Growth"));
        Assert.assertFalse(profile.getFeatureCardCounts("verb:tap").containsKey("Voltaic Key"));
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
