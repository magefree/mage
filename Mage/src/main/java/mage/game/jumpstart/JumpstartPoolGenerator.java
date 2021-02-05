package mage.game.jumpstart;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;

public class JumpstartPoolGenerator {

    /**
   * "Jumpstart is a new way to play Magic that mashes together themes from throughout the history of the game and
   * lets you skip the deckbuilding part. Grab two boosters, shuffle them into a 40-card deck, and start playing.
   * The set contains almost 500 reprints but also introduces 37 new cards that were designed to help fill out some
   * of the themes. These are not going to be Standard-, Pioneer-, or Modern-legal cards, but are legal in Eternal
   * formats (Legacy, Vintage, Pauper and Commander). There are no foil cards in the set. 120 cards are from Core
   * Set 2021[7], and more than 400 reprints are from sets before Core Set 2021. Cards from M21 have the M21 expansion
   * symbol, while new cards and other reprints will have the Jumpstart symbol. Reprints have a Post-M15 card frame.
   * The new cards and cards with new art are numbered #1/78 to #78/78 (40 basic lands, TBA common, TBA uncommon,
   * TBA rare, and TBA mythic rare). The other reprints with the Jumpstart expansion symbol are numbered #79 to 495."
     *
   * "Jumpstart is sold in 20-card boosters, and booster boxes contain 24 boosters.[9] All 20 cards in a booster fit a
   * theme, and most themes are mono-color and have multiple variations of cards included in the pack, totaling 121
   * different possible pack contents. Special "Mythic Rare" packs don't have variations at all, but just one possible
   * card list and can feature multiple colors. What theme each booster contains is randomized. Most packs are
   * singletons, but there are some instances of having two copies of a card in the pack. All boosters contain at
   * least one rare, and one in three boosters includes an extra rare. Of the 20 cards in a pack, seven or eight
   * are lands. One basic land (or in the case of Rainbow, Terramorphic Expanse) features art that matches the pack's
   * theme. The planeswalker themed packs use the respective Showcase lands from M21, but the other packs use brand-new
   * themed land art created for the set. The booster pack is wrapped like a regular pack, but the set of cards is
   * packed in an additional plastic wrap, with a face card (the "Pack Summary card") that indicates the theme and
   * the color of the half-deck."
     *
     * Source: https://mtg.gamepedia.com/Jumpstart
     *
   * Announcement: https://magic.wizards.com/en/articles/archive/news/introducing-jumpstart-new-way-play-magic-2020-02-20
   * Card Pool: https://magic.wizards.com/en/articles/archive/card-image-gallery/jumpstart
   * Deck Lists: https://magic.wizards.com/en/articles/archive/feature/jumpstart-decklists-2020-06-18
     */
    private static final String RESOURCE_NAME = "jumpstart/jumpstart.txt";
    private static final List<JumpstartPack> JUMPSTART_PACKS;

    static {
        List<JumpstartPack> packs = getPacks ("", true);
        JUMPSTART_PACKS = Collections.unmodifiableList(packs);
    }

    private static List<JumpstartPack> getPacks(String jumpstartPacks, boolean useDefault) {
        try {
            CharSource source;
            if (useDefault) {
                source = Resources.asCharSource(Resources.getResource(RESOURCE_NAME), Charsets.UTF_8);
            } else {
                source = CharSource.wrap(jumpstartPacks);
            }
            List<JumpstartPack> packs = new ArrayList<>();
            JumpstartPack pack = new JumpstartPack();
            packs.add(pack);
            for (String line : source.readLines()) {
                if (line.isEmpty()) {
                    if (!pack.isEmpty()) {
                        pack = new JumpstartPack();
                        packs.add(pack);
                    }
                } else if (line.startsWith("#")) {
                    // skip comment
                } else {
                    String[] ls = line.split(" ", 4);
                    int quantity = Integer.parseInt(ls[0]);
                    for (int i = 0; i < quantity; i++) {
                        pack.add(new DeckCardInfo(ls[3], ls[2], ls[1]));
                    }
                }
            }
            return packs;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Set<Card> doGeneratePool(List<JumpstartPack> packs) {
        try {
            DeckCardLists list = new DeckCardLists();
            SecureRandom random = new SecureRandom();
            for (int i = 0; i < 2; i++) {
                int index = random.nextInt(packs.size());
                list.getCards().addAll(packs.get(index).getCards());
            }
            return Deck.load(list, false, false).getCards();
        } catch (GameException e) {
            throw new RuntimeException(e);
        }
    }

    /* Notes
   *
   * 1) the pools generated by this method are for how the prerelease will be played (see https://mtg.gamepedia.com/Jumpstart#Events)
   *    In order to support the 4 pack version, xmage would need to support editing multiple decks and some Duo format
   *    similar to https://mtg.gamepedia.com/Duo_Standard
   *
   * 2) this treats all packs to have similar chance to open (packs will actually be opened at a particular rarity). This
   *    could be implemented if you know the various ratios between packs by knowing the rarities from this source:
   *    https://mtg.gamepedia.com/Jumpstart#Themes_and_mechanics
   *
   * 3) this does not attempt to add an additional rare 1/3 of the time as described here:
   *    https://mtg.gamepedia.com/Jumpstart#Marketing
     */
    public static Set<Card> generatePool() {
        return doGeneratePool(JUMPSTART_PACKS);
    }

    public static Set<Card> generatePool(String userJumpstartPacks) {
        if (userJumpstartPacks == null || userJumpstartPacks.length() > 300000) {
            return generatePool();
        }
        List<JumpstartPack> packs = getPacks(userJumpstartPacks, false);
        return doGeneratePool(packs);
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
