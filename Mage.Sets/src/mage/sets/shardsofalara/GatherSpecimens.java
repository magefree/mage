/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
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
 */
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import static mage.constants.Zone.EXILED;
import static mage.constants.Zone.GRAVEYARD;
import static mage.constants.Zone.HAND;
import static mage.constants.Zone.LIBRARY;
import static mage.constants.Zone.PICK;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentCard;

/**
 *
 * @author jeffwadsworth
 */
public class GatherSpecimens extends CardImpl<GatherSpecimens> {

    public GatherSpecimens(UUID ownerId) {
        super(ownerId, 45, "Gather Specimens", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{U}{U}{U}");
        this.expansionSetCode = "ALA";

        this.color.setBlue(true);

        // If a creature would enter the battlefield under an opponent's control this turn, it enters the battlefield under your control instead.
        this.getSpellAbility().addEffect(new GatherSpecimensReplacementEffect());

    }

    public GatherSpecimens(final GatherSpecimens card) {
        super(card);
    }

    @Override
    public GatherSpecimens copy() {
        return new GatherSpecimens(this);
    }
}

class GatherSpecimensReplacementEffect extends ReplacementEffectImpl<GatherSpecimensReplacementEffect> {

    public GatherSpecimensReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.GainControl);
        staticText = "If a creature would enter the battlefield under an opponent's control this turn, it enters the battlefield under your control instead";
    }

    public GatherSpecimensReplacementEffect(final GatherSpecimensReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GatherSpecimensReplacementEffect copy() {
        return new GatherSpecimensReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getFromZone() == Zone.HAND
                || ((ZoneChangeEvent) event).getFromZone() == Zone.EXILED
                || ((ZoneChangeEvent) event).getFromZone() == Zone.LIBRARY
                || ((ZoneChangeEvent) event).getFromZone() == Zone.GRAVEYARD
                || ((ZoneChangeEvent) event).getFromZone() == Zone.PICK) {
            Card card = game.getCard(((ZoneChangeEvent) event).getTargetId());
            game.replaceEvent(event);
            if (card != null) {
                Zone currentZone = game.getState().getZone(card.getId());
                ZoneChangeEvent event2 = new ZoneChangeEvent(card.getId(), source.getSourceId(), source.getControllerId(), currentZone, Zone.BATTLEFIELD);
                if (currentZone != null) {
                    boolean removed = false;
                    switch (currentZone) {
                        case GRAVEYARD:
                            removed = game.getPlayer(card.getOwnerId()).removeFromGraveyard(card, game);
                            break;
                        case HAND:
                            removed = game.getPlayer(card.getOwnerId()).removeFromHand(card, game);
                            break;
                        case LIBRARY:
                            removed = game.getPlayer(card.getOwnerId()).removeFromLibrary(card, game);
                            break;
                        case EXILED:
                            game.getExile().removeCard(card, game);
                            removed = true;
                            break;
                        case PICK:
                            removed = true;
                            break;
                        default:
                            System.out.println("putOntoBattlefield, not fully implemented: fromZone=" + currentZone);
                    }
                    game.rememberLKI(card.getId(), event2.getFromZone(), card);
                    if (!removed) {
                        System.out.println("Couldn't find card in fromZone, card=" + card.getName() + ", fromZone=" + currentZone);
                    }
                }
                PermanentCard permanent = new PermanentCard(card, source.getControllerId());
                game.resetForSourceId(permanent.getId());
                game.addPermanent(permanent);
                game.setZone(card.getId(), Zone.BATTLEFIELD);
                game.setScopeRelevant(true);
                game.applyEffects();
                permanent.entersBattlefield(source.getSourceId(), game, currentZone, true);
                game.setScopeRelevant(false);
                game.applyEffects();
                game.fireEvent(new ZoneChangeEvent(permanent, source.getControllerId(), currentZone, Zone.BATTLEFIELD));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getToZone() == Zone.BATTLEFIELD) {
            Card card = game.getCard(((ZoneChangeEvent) event).getTargetId());
            if (card != null
                    && card.getCardType().contains(CardType.CREATURE)
                    && card.getOwnerId() != source.getControllerId()) {
                return true;
            }
        }
        return false;
    }
}