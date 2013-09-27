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

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
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

    public LookLibraryAndPickControllerEffect(DynamicValue numberOfCards, boolean mayShuffleAfter, DynamicValue numberToPick, FilterCard pickFilter, boolean putOnTop) {
            this(numberOfCards, mayShuffleAfter, numberToPick, pickFilter, putOnTop, true);
    }

    public LookLibraryAndPickControllerEffect(DynamicValue numberOfCards, boolean mayShuffleAfter, DynamicValue numberToPick, FilterCard pickFilter, boolean putOnTop, boolean reveal) {
            this(numberOfCards, mayShuffleAfter, numberToPick, pickFilter, Zone.LIBRARY, putOnTop, reveal);
    }

    public LookLibraryAndPickControllerEffect(DynamicValue numberOfCards, boolean mayShuffleAfter, DynamicValue numberToPick, FilterCard pickFilter, Zone targetZoneLookedCards, boolean putOnTop, boolean reveal) {
        this(numberOfCards, mayShuffleAfter, numberToPick, pickFilter, targetZoneLookedCards, putOnTop, reveal, false);
    }

    public LookLibraryAndPickControllerEffect(int numberOfCards, int numberToPick, FilterCard pickFilter, boolean upTo) {
        this(new StaticValue(numberOfCards), false, new StaticValue(numberToPick), pickFilter, Zone.LIBRARY, false, true, upTo);
    }

    public LookLibraryAndPickControllerEffect(int numberOfCards, int numberToPick, FilterCard pickFilter, boolean reveal, boolean upTo, Zone targetZonePickedCards, boolean optional) {
        this(new StaticValue(numberOfCards), false, new StaticValue(numberToPick), pickFilter, Zone.LIBRARY, false, reveal, upTo, targetZonePickedCards, optional);

    }

    public LookLibraryAndPickControllerEffect(DynamicValue numberOfCards, boolean mayShuffleAfter, DynamicValue numberToPick, FilterCard pickFilter, Zone targetZoneLookedCards, boolean putOnTop, boolean reveal, boolean upTo) {
        this(numberOfCards, mayShuffleAfter, numberToPick, pickFilter, targetZoneLookedCards, putOnTop, reveal, upTo, Zone.HAND, false);
    }

    public LookLibraryAndPickControllerEffect(DynamicValue numberOfCards, boolean mayShuffleAfter, DynamicValue numberToPick, FilterCard pickFilter, Zone targetZoneLookedCards, boolean putOnTop, boolean reveal, boolean upTo, Zone targetZonePickedCards, boolean optional) {
            super(Outcome.DrawCard, numberOfCards, mayShuffleAfter, targetZoneLookedCards, putOnTop);
            this.numberToPick = numberToPick;
            this.filter = pickFilter;
            this.revealPickedCards = reveal;
            this.targetPickedCards = targetZonePickedCards;
            this.upTo = upTo;
            this.optional = optional;
    }

    public LookLibraryAndPickControllerEffect(final LookLibraryAndPickControllerEffect effect) {
        super(effect);
        this.numberToPick = effect.numberToPick.copy();
        this.filter = effect.filter.copy();
        this.revealPickedCards = effect.revealPickedCards;
        this.targetPickedCards = effect.targetPickedCards;
        this.upTo = effect.upTo;
        this.optional = effect.optional;
    }

    @Override
    public LookLibraryAndPickControllerEffect copy() {
        return new LookLibraryAndPickControllerEffect(this);

    }
    @Override
    protected void cardLooked(Card card, Game game, Ability source) {
        if (numberToPick.calculate(game, source) > 0 && filter.match(card, game)) {
            ++foundCardsToPick;
        }
    }

    @Override
    protected void actionWithSelectedCards(Cards cards, Game game, Ability source, String windowName) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && foundCardsToPick > 0) {
            if (!optional || player.chooseUse(Outcome.DrawCard, getMayText(), game)) {
                FilterCard pickFilter = filter.copy();
                pickFilter.setMessage(getPickText());
                TargetCard target = new TargetCard((upTo ? 0:numberToPick.calculate(game, source)),numberToPick.calculate(game, source), Zone.PICK, pickFilter);
                if (player.choose(Outcome.DrawCard, cards, target, game)) {
                    Cards reveal = new CardsImpl();
                    for (UUID cardId : (List<UUID>)target.getTargets()) {
                        Card card = cards.get(cardId, game);
                        if (card != null) {
                            cards.remove(card);
                            if (targetZoneLookedCards.equals(Zone.BATTLEFIELD)) {
                                card.putOntoBattlefield(game, Zone.PICK, source.getSourceId(), source.getControllerId());
                            } else {
                                card.moveToZone(targetPickedCards, source.getId(), game, false);
                            }
                            if (revealPickedCards) {
                                reveal.add(card);
                            }
                        }
                    }
                    if (revealPickedCards) {
                        player.revealCards(windowName, reveal, game);
                    }

                }
            }
        }

    }

    private String getMayText() {
        StringBuilder sb = new StringBuilder("Do you wish to ");
        switch(targetPickedCards) {
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
        return sb.append("?").toString();
    }

    private String getPickText() {
        StringBuilder sb = new StringBuilder(filter.getMessage()).append(" to ");
        switch(targetPickedCards) {
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
        StringBuilder sb = new StringBuilder();
        if (numberToPick.calculate(null, null) > 0) {
            
                if (revealPickedCards) {
                    sb.append(". You may reveal ");
                    sb.append(filter.getMessage()).append(" from among them and put it into your ");
                } else {
                    if (targetPickedCards.equals(Zone.BATTLEFIELD)) {
                        sb.append(". You ");
                        if (optional) {
                            sb.append("may ");
                        }
                        sb.append("put ").append(filter.getMessage()).append(" from among them onto the ");
                    } else {
                        sb.append(". Put one of them into your ");
                    }
                }
                sb.append(targetPickedCards.toString().toLowerCase());

            if (targetZoneLookedCards == Zone.LIBRARY) {
                sb.append(". Put the rest ");
                if (putOnTop) {
                    sb.append("back ");
                } else {
                    sb.append("on the bottom of your library ");
                }
                sb.append("in any order");
            } else if (targetZoneLookedCards == Zone.GRAVEYARD) {
                sb.append(" and the other into your graveyard");
            }
        }
        // get text frame from super class and inject action text
        return setText(mode, sb.toString());
    }    

}
