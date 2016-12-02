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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class MagusOfTheWill extends CardImpl {

    public MagusOfTheWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{B}, {T}, Exile Magus of the Will: Until end of turn, you may play cards from your graveyard.
        // If a card would be put into your graveyard from anywhere else this turn, exile that card instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CanPlayCardsFromGraveyardEffect(), new ManaCostsImpl("{2}{B}"));
        ability.addEffect(new MagusOfTheWillReplacementEffect());
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    public MagusOfTheWill(final MagusOfTheWill card) {
        super(card);
    }

    @Override
    public MagusOfTheWill copy() {
        return new MagusOfTheWill(this);
    }
}

class CanPlayCardsFromGraveyardEffect extends ContinuousEffectImpl {

    public CanPlayCardsFromGraveyardEffect() {
        this(Duration.EndOfTurn);
    }

    public CanPlayCardsFromGraveyardEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "Until end of turn, you may play cards from your graveyard";
    }

    public CanPlayCardsFromGraveyardEffect(final CanPlayCardsFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public CanPlayCardsFromGraveyardEffect copy() {
        return new CanPlayCardsFromGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.setPlayCardsFromGraveyard(true);
            return true;
        }
        return false;
    }

}

class MagusOfTheWillReplacementEffect extends ReplacementEffectImpl {

    public MagusOfTheWillReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        this.staticText = "If a card would be put into your graveyard from anywhere else this turn, exile that card instead";
    }

    public MagusOfTheWillReplacementEffect(final MagusOfTheWillReplacementEffect effect) {
        super(effect);
    }

    @Override
    public MagusOfTheWillReplacementEffect copy() {
        return new MagusOfTheWillReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
                Permanent permanent = ((ZoneChangeEvent) event).getTarget();
                if (permanent != null) {
                    return controller.moveCardToExileWithInfo(permanent, null, "", source.getSourceId(), game, ((ZoneChangeEvent) event).getFromZone(), true);
                }
            } else {
                Card card = game.getCard(event.getTargetId());
                if (card != null) {
                    return controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, ((ZoneChangeEvent) event).getFromZone(), true);
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.getOwnerId().equals(source.getControllerId())) {
                Permanent permanent = ((ZoneChangeEvent) event).getTarget();
                if (permanent == null || !(permanent instanceof PermanentToken)) {
                    return true;
                }
            }
        }
        return false;
    }

}
