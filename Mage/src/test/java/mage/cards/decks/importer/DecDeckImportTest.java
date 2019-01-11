package mage.cards.decks.importer;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;

public class DecDeckImportTest {

  private static final FakeCardLookup LOOKUP = new FakeCardLookup()
      .addCard("Masticore")
      .addCard("Metalworker")
      .addCard("Phyrexian Colossus")
      .addCard("Crumbling Sanctuary")
      .addCard("Grim Monolith")
      .addCard("Mishra's Helix")
      .addCard("Phyrexian Processor")
      .addCard("Tangle Wire")
      .addCard("Thran Dynamo")
      .addCard("Voltaic Key")
      .addCard("Tinker")
      .addCard("Brainstorm")
      .addCard("Crystal Vein")
      .addCard("Island")
      .addCard("Rishadan Port")
      .addCard("Saprazzan Skerry")
      .addCard("Annul")
      .addCard("Chill")
      .addCard("Miscalculation")
      .addCard("Mishra's Helix")
      .addCard("Rising Waters");

  @Test
  public void testImport() {
    StringBuilder errors = new StringBuilder();
    DecDeckImporter importer = new DecDeckImporter() {
      @Override
      public CardLookup getCardLookup() {
        return LOOKUP;
      }
    };
    DeckCardLists deck = importer.importDeck(
        "src/test/java/mage/cards/decks/importer/testdeck.dec", errors);

    TestDeckChecker.checker()
      .addMain("Masticore", 4)
      .addMain("Metalworker", 4)
      .addMain("Phyrexian Colossus", 1)
      .addMain("Crumbling Sanctuary", 1)
      .addMain("Grim Monolith", 4)
      .addMain("Mishra's Helix", 1)
      .addMain("Phyrexian Processor", 4)
      .addMain("Tangle Wire", 4)
      .addMain("Thran Dynamo", 4)
      .addMain("Voltaic Key", 4)
      .addMain("Tinker", 4)
      .addMain("Brainstorm", 4)
      .addMain("Crystal Vein", 4)
      .addMain("Island", 9)
      .addMain("Rishadan Port", 4)
      .addMain("Saprazzan Skerry", 4)
      .addSide("Annul", 4)
      .addSide("Chill", 4)
      .addSide("Miscalculation", 4)
      .addSide("Mishra's Helix", 1)
      .addSide("Rising Waters", 2)
      .verify(deck, 60, 15);

    assertEquals("", errors.toString());
  }

  private static FakeCardLookup getFakeCardLookup() {
    FakeCardLookup lookup = new FakeCardLookup() {
      @Override
      public Optional<CardInfo> lookupCardInfo(String name) {
        System.out.println(name);
        return super.lookupCardInfo(name);
      }
    };
    return lookup;
  }

}
