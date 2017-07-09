/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX
 */
public class LookLibraryControllerEffect extends OneShotEffect {

    protected DynamicValue numberOfCards;
    protected boolean mayShuffleAfter = false;
    protected boolean putOnTop = true; // if false on put rest back on bottom of library
    protected Zone targetZoneLookedCards; // GRAVEYARD, LIBRARY
    protected boolean backInRandomOrder = false;

    public LookLibraryControllerEffect() {
        this(1);
    }

    public LookLibraryControllerEffect(int numberOfCards) {
        this(numberOfCards, false, true);
    }

    public LookLibraryControllerEffect(DynamicValue numberOfCards) {
        this(numberOfCards, false, true);
    }

    public LookLibraryControllerEffect(int numberOfCards, boolean mayShuffleAfter) {
        this(numberOfCards, mayShuffleAfter, true);
    }

    public LookLibraryControllerEffect(int numberOfCards, boolean mayShuffleAfter, boolean putOnTop) {
        this(new StaticValue(numberOfCards), mayShuffleAfter, putOnTop);
    }

    public LookLibraryControllerEffect(DynamicValue numberOfCards, boolean mayShuffleAfter, boolean putOnTop) {
        this(Outcome.Benefit, numberOfCards, mayShuffleAfter, Zone.LIBRARY, putOnTop);
    }

    public LookLibraryControllerEffect(Outcome outcome, DynamicValue numberOfCards, boolean mayShuffleAfter, Zone targetZoneLookedCards, boolean putOnTop) {
        super(outcome);
        this.numberOfCards = numberOfCards;
        this.mayShuffleAfter = mayShuffleAfter;
        this.targetZoneLookedCards = targetZoneLookedCards;
        this.putOnTop = putOnTop;

    }

    public LookLibraryControllerEffect(final LookLibraryControllerEffect effect) {
        super(effect);
        this.numberOfCards = effect.numberOfCards.copy();
        this.mayShuffleAfter = effect.mayShuffleAfter;
        this.targetZoneLookedCards = effect.targetZoneLookedCards;
        this.putOnTop = effect.putOnTop;
        this.backInRandomOrder = effect.backInRandomOrder;
    }

    @Override
    public LookLibraryControllerEffect copy() {
        return new LookLibraryControllerEffect(this);

    }

    @Override
    public boolean apply(Game game, Ability source) {
        String windowName = "Reveal";

        if (source instanceof SpellAbility) {
            Card sourceCard = game.getCard(source.getSourceId());
            if (sourceCard != null) {
                windowName = sourceCard.getIdName();
            }
        } else {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null) {
                windowName = sourcePermanent.getIdName();
            }
        }

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        // take cards from library and look at them
        boolean topCardRevealed = player.isTopCardRevealed();
        player.setTopCardRevealed(false);
        Cards cards = new CardsImpl();
        int count = Math.min(player.getLibrary().size(), this.numberOfCards.calculate(game, source, this));
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                this.cardLooked(card, game, source);
            }
        }
        player.lookAtCards(windowName, cards, game);

        this.actionWithSelectedCards(cards, game, source, windowName);

        this.putCardsBack(source, player, cards, game);

        player.setTopCardRevealed(topCardRevealed);

        this.mayShuffle(player, source, game);

        return true;
    }

    public boolean isBackInRandomOrder() {
        return backInRandomOrder;
    }

    public Effect setBackInRandomOrder(boolean backInRandomOrder) {
        this.backInRandomOrder = backInRandomOrder;
        return this;
    }

    protected void cardLooked(Card card, Game game, Ability source) {
    }

    protected void actionWithSelectedCards(Cards cards, Game game, Ability source, String windowName) {
    }

    /**
     * Put the rest of the cards back to defined zone
     *
     * @param source
     * @param player
     * @param cards
     * @param game
     */
    protected void putCardsBack(Ability source, Player player, Cards cards, Game game) {
        switch (targetZoneLookedCards) {
            case LIBRARY:
                if (putOnTop) {
                    player.putCardsOnTopOfLibrary(cards, game, source, true);
                } else {
                    if (backInRandomOrder) {
                        Cards newOrder = new CardsImpl();
                        while (!cards.isEmpty()) {
                            Card card = cards.getRandom(game);
                            newOrder.add(card);
                            cards.remove(card);
                        }
                        cards = newOrder;
                    }
                    player.putCardsOnBottomOfLibrary(cards, game, source, true);
                }
                break;
            case GRAVEYARD:
                player.moveCards(cards, Zone.GRAVEYARD, source, game);
                break;
            default:
            // not supported yet
        }
    }

    /**
     * Check to shuffle library if allowed
     *
     * @param player
     * @param source
     * @param game
     */
    protected void mayShuffle(Player player, Ability source, Game game) {
        if (this.mayShuffleAfter && player.chooseUse(Outcome.Benefit, "Shuffle your library?", source, game)) {
            player.shuffleLibrary(source, game);
        }
    }

    @Override
    public String getText(Mode mode) {
        return setText(mode, "");
    }

    public String setText(Mode mode, String middleText) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        int numberLook;
        try {
            numberLook = Integer.parseInt(numberOfCards.toString());
        } catch (NumberFormatException e) {
            numberLook = 0;
        }
        StringBuilder sb = new StringBuilder("look at the top ");
        switch (numberLook) {
            case 0:
                sb.append(" X ");
                break;
            case 1:
                sb.append("card ");
                break;
            default:
                sb.append(CardUtil.numberToText(numberLook));
                break;
        }
        if (numberLook != 1) {
            sb.append(" cards ");
        }

        sb.append("of your Library");
        if (numberLook == 0) {
            sb.append(", where {X} is the number of cards ").append(numberOfCards.getMessage());
        }

        if (!middleText.isEmpty()) {
            sb.append(middleText);
        } else if (numberLook > 1) {
            if (backInRandomOrder) {
                sb.append(". Put the rest on the bottom of your library in a random order");
            } else {
                sb.append(", then put them back in any order");
            }
        }
        if (this.mayShuffleAfter) {
            sb.append(". You may shuffle your library");
        }

        return sb.toString();
    }
}
