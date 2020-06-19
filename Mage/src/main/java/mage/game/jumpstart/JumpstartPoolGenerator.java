package mage.game.jumpstart;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;

public class JumpstartPoolGenerator {

  private static final String RESOURCE_NAME = "jumpstart/jumpstart.txt";
  private static final List<JumpstartPack> JUMPSTART_PACKS;

  static {
    try {
      CharSource source = Resources.asCharSource(Resources.getResource(RESOURCE_NAME), Charsets.UTF_8);
      List<JumpstartPack> packs = new ArrayList<>();
      JumpstartPack pack = new JumpstartPack();
      for (String line : source.readLines()) {
        if (line.isEmpty()) {
          if (!pack.isEmpty()) {
            packs.add(pack);
            pack = new JumpstartPack();
          }
        } else if (line.startsWith("#")) {
          // skip comment
        } else {
          String[] ls = line.split(" ", 3);
          pack.add(new DeckCardInfo(ls[2], ls[1], ls[0]));
        }
      }
      JUMPSTART_PACKS = Collections.unmodifiableList(packs);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public static Set<Card> generatePool() {
    try {
      DeckCardLists list = new DeckCardLists();
      SecureRandom random = new SecureRandom();
      for (int i = 0; i < 2; i++) {
        int index = random.nextInt(JUMPSTART_PACKS.size());
        list.getCards().addAll(JUMPSTART_PACKS.get(index).getCards());
      }
      return Deck.load(list).getCards();
    } catch (GameException e) {
      throw new RuntimeException(e);
    }
  }

  public static class JumpstartPack {

    private final List<DeckCardInfo> cards = new ArrayList<>();

    public void add(DeckCardInfo card) {
      cards.add(card);
    }

    public boolean isEmpty() {
      return cards.isEmpty();
    }

    public List<DeckCardInfo> getCards() {
      return Collections.unmodifiableList(cards);
    }

  }

}
