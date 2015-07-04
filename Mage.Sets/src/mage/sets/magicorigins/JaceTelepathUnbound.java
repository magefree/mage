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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class JaceTelepathUnbound extends CardImpl {

    public JaceTelepathUnbound(UUID ownerId) {
        super(ownerId, 60, "Jace, Telepath Unbound", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "");
        this.expansionSetCode = "ORI";
        this.subtype.add("Jace");

        this.nightCard = true;
        this.canTransform = true;

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(5)), false));

        // +1: Up to one target creature gets -2/-0 until your next turn.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(-2, 0, Duration.UntilYourNextTurn), 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -3: You may cast target instant or sorcery card from your graveyard this turn. If that card would be put into your graveyard this turn, exile it instead.
        ability = new LoyaltyAbility(new JaceTelepathUnboundEffect(), -3);
        ability.addTarget(new TargetCardInGraveyard(new FilterInstantOrSorceryCard()));
        this.addAbility(ability);

        // -9: You get an emblem with "Whenever you cast a spell, target opponent puts the top five cards of his or her library into his or her graveyard".
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new JaceTelepathUnboundEmblem()), -9));
    }

    public JaceTelepathUnbound(final JaceTelepathUnbound card) {
        super(card);
    }

    @Override
    public JaceTelepathUnbound copy() {
        return new JaceTelepathUnbound(this);
    }
}

class JaceTelepathUnboundEffect extends OneShotEffect {

    JaceTelepathUnboundEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may cast target instant or sorcery card from your graveyard this turn. If that card would be put into your graveyard this turn, exile it instead";
    }

    JaceTelepathUnboundEffect(final JaceTelepathUnboundEffect effect) {
        super(effect);
    }

    @Override
    public JaceTelepathUnboundEffect copy() {
        return new JaceTelepathUnboundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null) {
            ContinuousEffect effect = new JaceTelepathUnboundCastFromGraveyardEffect();
            effect.setTargetPointer(new FixedTarget(card.getId()));
            game.addEffect(effect, source);
            effect = new JaceTelepathUnboundReplacementEffect(card.getId());
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class JaceTelepathUnboundCastFromGraveyardEffect extends AsThoughEffectImpl {

    JaceTelepathUnboundCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
    }

    JaceTelepathUnboundCastFromGraveyardEffect(final JaceTelepathUnboundCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public JaceTelepathUnboundCastFromGraveyardEffect copy() {
        return new JaceTelepathUnboundCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return objectId.equals(this.getTargetPointer().getFirst(game, source)) && affectedControllerId.equals(source.getControllerId());
    }
}

class JaceTelepathUnboundReplacementEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    JaceTelepathUnboundReplacementEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
        staticText = "If that card would be put into your graveyard this turn, exile it instead";
    }

    JaceTelepathUnboundReplacementEffect(final JaceTelepathUnboundReplacementEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public JaceTelepathUnboundReplacementEffect copy() {
        return new JaceTelepathUnboundReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(this.cardId);
        if (controller != null && card != null) {
            controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.STACK, true);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getTargetId().equals(this.cardId);
    }
}

class JaceTelepathUnboundEmblem extends Emblem {

    //  You get an emblem with "Whenever you cast a spell, target opponent puts the top five cards of his or her library into his or her graveyard".

    public JaceTelepathUnboundEmblem() {
        this.setName("Emblem - Jace");
        Effect effect = new PutTopCardOfLibraryIntoGraveTargetEffect(5);
        effect.setText("target opponent puts the top five cards of his or her library into his or her graveyard");
        Ability ability = new SpellCastControllerTriggeredAbility(effect, false);
        ability.addTarget(new TargetOpponent());
        getAbilities().add(ability);
    }
}
