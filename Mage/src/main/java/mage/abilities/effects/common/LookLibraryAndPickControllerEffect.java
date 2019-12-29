/*
 *
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 *
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.Locale;

import static java.lang.Integer.min;

/**
 * @author LevelX
 */
public class LookLibraryAndPickControllerEffect extends LookLibraryControllerEffect {

    protected FilterCard filter; // which kind of cards to reveal
    protected DynamicValue numberToPick;
    protected boolean revealPickedCards = true;
    protected Zone targetPickedCards = Zone.HAND; // HAND
    protected int foundCardsToPick = 0;
    protected boolean optional;
    private boolean upTo;
    private boolean putOnTopSelected;
    private boolean anyOrder;

    //TODO: These constructors are a mess
    public LookLibraryAndPickControllerEffect(DynamicValue numberOfCards,
                                              boolean mayShuffleAfter, DynamicValue numberToPick,
                                              FilterCard pickFilter, boolean putOnTop) {
        this(numberOfCards, mayShuffleAfter, numberToPick, pickFilter,
                putOnTop, true);
    }

    public LookLibraryAndPickControllerEffect(DynamicValue numberOfCards,
                                              boolean mayShuffleAfter, DynamicValue numberToPick,
                                              FilterCard pickFilter, boolean putOnTop, boolean reveal) {
        this(numberOfCards, mayShuffleAfter, numberToPick, pickFilter,
                Zone.LIBRARY, putOnTop, reveal);
    }

    public LookLibraryAndPickControllerEffect(DynamicValue numberOfCards,
                                              boolean mayShuffleAfter, DynamicValue numberToPick,
                                              FilterCard pickFilter, Zone targetZoneLookedCards,
                                              boolean putOnTop, boolean reveal) {
        this(numberOfCards, mayShuffleAfter, numberToPick, pickFilter,
                targetZoneLookedCards, putOnTop, reveal, reveal);
    }

    public LookLibraryAndPickControllerEffect(int numberOfCards,
                                              int numberToPick, FilterCard pickFilter, boolean upTo) {
        this(new StaticValue(numberOfCards), false,
                new StaticValue(numberToPick), pickFilter, Zone.LIBRARY, false,
                true, upTo);
    }

    /**
     * @param numberOfCards
     * @param numberToPick
     * @param pickFilter
     * @param reveal
     * @param upTo
     * @param targetZonePickedCards
     * @param optional
     */
    public LookLibraryAndPickControllerEffect(int numberOfCards,
                                              int numberToPick, FilterCard pickFilter, boolean reveal,
                                              boolean upTo, Zone targetZonePickedCards, boolean optional) {
        this(new StaticValue(numberOfCards), false,
                new StaticValue(numberToPick), pickFilter, Zone.LIBRARY, false,
                reveal, upTo, targetZonePickedCards, optional, true, true);

    }

    /**
     * @param numberOfCards
     * @param mayShuffleAfter
     * @param numberToPick
     * @param pickFilter
     * @param targetZoneLookedCards
     * @param putOnTop              if zone for the rest is library decide if cards go to top
     *                              or bottom
     * @param reveal
     * @param upTo
     */
    public LookLibraryAndPickControllerEffect(DynamicValue numberOfCards,
                                              boolean mayShuffleAfter, DynamicValue numberToPick,
                                              FilterCard pickFilter, Zone targetZoneLookedCards,
                                              boolean putOnTop, boolean reveal, boolean upTo) {
        this(numberOfCards, mayShuffleAfter, numberToPick, pickFilter,
                targetZoneLookedCards, putOnTop, reveal, upTo, Zone.HAND,
                false, true, true);
    }

    /**
     * @param numberOfCards
     * @param mayShuffleAfter
     * @param numberToPick
     * @param pickFilter
     * @param targetZoneLookedCards
     * @param putOnTop              if zone for the rest is library decide if cards go to top
     *                              or bottom
     * @param reveal
     * @param upTo
     * @param targetZonePickedCards
     * @param optional
     */
    public LookLibraryAndPickControllerEffect(DynamicValue numberOfCards,
                                              boolean mayShuffleAfter, DynamicValue numberToPick,
                                              FilterCard pickFilter, Zone targetZoneLookedCards, boolean putOnTop,
                                              boolean reveal, boolean upTo, Zone targetZonePickedCards,
                                              boolean optional) {

        this(numberOfCards, mayShuffleAfter, numberToPick, pickFilter,
                targetZoneLookedCards, putOnTop, reveal, upTo,
                targetZonePickedCards, optional, true, true);
    }

    /**
     * @param numberOfCards
     * @param mayShuffleAfter
     * @param numberToPick
     * @param pickFilter
     * @param targetZoneLookedCards
     * @param putOnTop              if zone for the rest is library decide if cards go to top
     *                              or bottom
     * @param reveal
     * @param upTo
     * @param targetZonePickedCards
     * @param optional
     * @param putOnTopSelected
     * @param anyOrder
     */
    public LookLibraryAndPickControllerEffect(DynamicValue numberOfCards,
                                              boolean mayShuffleAfter, DynamicValue numberToPick,
                                              FilterCard pickFilter, Zone targetZoneLookedCards, boolean putOnTop,
                                              boolean reveal, boolean upTo, Zone targetZonePickedCards,
                                              boolean optional, boolean putOnTopSelected, boolean anyOrder) {
        super(Outcome.DrawCard, numberOfCards, mayShuffleAfter,
                targetZoneLookedCards, putOnTop);
        this.numberToPick = numberToPick;
        this.filter = pickFilter;
        this.revealPickedCards = reveal;
        this.targetPickedCards = targetZonePickedCards;
        this.upTo = upTo;
        this.optional = optional;
        this.putOnTopSelected = putOnTopSelected;
        this.anyOrder = anyOrder;
    }

    public LookLibraryAndPickControllerEffect(final LookLibraryAndPickControllerEffect effect) {
        super(effect);
        this.numberToPick = effect.numberToPick.copy();
        this.filter = effect.filter.copy();
        this.revealPickedCards = effect.revealPickedCards;
        this.targetPickedCards = effect.targetPickedCards;
        this.upTo = effect.upTo;
        this.optional = effect.optional;
        this.putOnTopSelected = effect.putOnTopSelected;
        this.anyOrder = effect.anyOrder;
    }

    @Override
    public LookLibraryAndPickControllerEffect copy() {
        return new LookLibraryAndPickControllerEffect(this);

    }

    @Override
    protected void actionWithSelectedCards(Cards cards, Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && numberToPick.calculate(game, source, this) > 0
                && cards.count(filter, source.getSourceId(), source.getControllerId(), game) > 0) {
            if (!optional || player.chooseUse(Outcome.DrawCard, getMayText(), source, game)) {
                FilterCard pickFilter = filter.copy();
                pickFilter.setMessage(getPickText());
                int number = min(cards.size(), numberToPick.calculate(game, source, this));
                TargetCard target = new TargetCard((upTo ? 0 : number), number, Zone.LIBRARY, pickFilter);
                if (player.choose(Outcome.DrawCard, cards, target, game)) {
                    Cards pickedCards = new CardsImpl(target.getTargets());
                    cards.removeAll(pickedCards);
                    if (targetPickedCards == Zone.LIBRARY && !putOnTopSelected) {
                        player.putCardsOnBottomOfLibrary(pickedCards, game, source, anyOrder);
                    } else {
                        player.moveCards(pickedCards.getCards(game), targetPickedCards, source, game);
                    }
                    if (revealPickedCards) {
                        player.revealCards(source, pickedCards, game);
                    }

                }
            }
        }

    }

    private String getMayText() {
        StringBuilder sb = new StringBuilder("Do you wish to ");
        switch (targetPickedCards) {
            case HAND:
                if (revealPickedCards) {
                    sb.append("reveal ").append(filter.getMessage()).append(" and put into your hand");
                } else {
                    sb.append("put ").append(filter.getMessage()).append(" into your hand");
                }
                break;
            case BATTLEFIELD:
                sb.append("put ").append(filter.getMessage()).append(" onto the battlefield");
                break;
            case GRAVEYARD:
                sb.append("put ").append(filter.getMessage()).append(" into your graveyard");
                break;
        }
        return sb.append('?').toString();
    }

    private String getPickText() {
        StringBuilder sb = new StringBuilder(filter.getMessage()).append(" to ");
        switch (targetPickedCards) {
            case LIBRARY:
                if (putOnTopSelected) {
                    sb.append("put on the top of your library");
                } else {
                    sb.append("put on the bottom of your library");
                }
                if (anyOrder) {
                    sb.append(" in any order");
                } else {
                    sb.append(" in a random order");
                }
                break;
            case HAND:
                if (revealPickedCards) {
                    sb.append("reveal and put into your hand");
                } else {
                    sb.append("put into your hand");
                }
                break;
            case BATTLEFIELD:
                sb.append("put onto the battlefield");
                break;
            case GRAVEYARD:
                sb.append("put into the graveyard");
                break;
        }
        return sb.toString();
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (numberToPick.calculate(null, null, this) > 0) {

            if (revealPickedCards) {
                sb.append(". You may reveal ");
                sb.append(filter.getMessage()).append(" from among them and put it into your ");
            } else if (targetPickedCards == Zone.BATTLEFIELD) {
                sb.append(". ");
                if (optional) {
                    sb.append("You may p");
                } else {
                    sb.append('P');
                }
                sb.append("ut ").append(filter.getMessage()).append(" from among them onto the ");
            } else {
                sb.append(". Put ");
                if (numberToPick.calculate(null, null, this) > 1) {
                    if (upTo) {
                        if (numberToPick.calculate(null, null, this) == (numberOfCards.calculate(null, null, this))) {
                            sb.append("any number");
                        } else {
                            sb.append("up to ").append(CardUtil.numberToText(numberToPick.calculate(null, null, this)));
                        }
                    } else {
                        sb.append(CardUtil.numberToText(numberToPick.calculate(null, null, this)));
                    }
                } else {
                    sb.append("one");
                }

                sb.append(" of them into your ");
            }
            sb.append(targetPickedCards.toString().toLowerCase(Locale.ENGLISH));

            if (targetZoneLookedCards == Zone.LIBRARY) {
                sb.append(". Put the rest ");
                if (putOnTop) {
                    sb.append("back on top");
                } else {
                    sb.append("on the bottom");
                }
                sb.append(" of your library in ");
                if (anyOrder && !backInRandomOrder) {
                    sb.append("any");
                } else {
                    sb.append("a random");
                }
                sb.append(" order");
            } else if (targetZoneLookedCards == Zone.GRAVEYARD) {
                sb.append(" and the");
                if (numberOfCards instanceof StaticValue && numberToPick instanceof StaticValue
                        && ((StaticValue) numberToPick).getValue() + 1 == ((StaticValue) numberOfCards).getValue()) {
                    sb.append(" other");
                } else {
                    sb.append(" rest");
                }
                sb.append(" into your graveyard");
            }
        }
        // get text frame from super class and inject action text
        return setText(mode, sb.toString());
    }

}
