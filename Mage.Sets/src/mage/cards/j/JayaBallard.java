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
package mage.cards.j;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AddConditionalManaEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.discard.DiscardUpToNDrawThatManySourceEffect;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Will
 */
public class JayaBallard extends CardImpl {

    public JayaBallard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.JAYA);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +1: Add {R}{R}{R}. Spend this mana only to cast instant or sorcery spells.
        this.addAbility(
            new LoyaltyAbility(
                new AddConditionalManaEffect(
                    Mana.RedMana(3),
                    new ConditionalSpellManaBuilder(
                        new FilterInstantOrSorcerySpell())),
                1));

        // +1: Discard up to three cards, then draw that many cards.
        this.addAbility(
            new LoyaltyAbility(
                new DiscardUpToNDrawThatManySourceEffect(3),
                1));
        // −8: You get an emblem with "You may cast instant and sorcery cards from your graveyard. If a card cast this way would be put into your graveyard, exile it instead."
        this.addAbility(
            new LoyaltyAbility(
                new GetEmblemEffect(new JayaBallardEmblem()),
                -8));
    }

    public JayaBallard(final JayaBallard card) {
        super(card);
    }

    @Override
    public JayaBallard copy() {
        return new JayaBallard(this);
    }
}

class JayaBallardEmblem extends Emblem {
    // You may cast instant or sorcery cards from your graveyard. If a card cast this way would be put into your graveyard, exile it instead

    public JayaBallardEmblem() {
        this.setName("Emblem Jaya");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new JayaBallardEmblemContinuousEffect()));
    }
}

class JayaBallardEmblemContinuousEffect extends ContinuousEffectImpl {

    private static final FilterCard filter = new FilterCard("Instant or sorcery spell");

    static {
        filter.add(Predicates.or(
            new CardTypePredicate(CardType.INSTANT),
            new CardTypePredicate(CardType.SORCERY)
        ));
    }

    JayaBallardEmblemContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "You may cast instant or sorcery cards from your graveyard. If a card cast this way would be put into your graveyard, exile it instead";
    }

    JayaBallardEmblemContinuousEffect(final JayaBallardEmblemContinuousEffect effect) {
        super(effect);
    }

    @Override
    public JayaBallardEmblemContinuousEffect copy() {
        return new JayaBallardEmblemContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Card card : player.getGraveyard().getCards(filter, game)) {              
            ContinuousEffect effect = new JayaBallardEmblemCastFromGraveyardEffect();
            effect.setTargetPointer(new FixedTarget(card.getId()));
            game.addEffect(effect, source);
            effect = new JayaBallardEmblemReplacementEffect(card.getId());
            game.addEffect(effect, source);
        }
        return true;
    }
}

class JayaBallardEmblemCastFromGraveyardEffect extends AsThoughEffectImpl {

    JayaBallardEmblemCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast instant and sorcery cards from your graveyard";
    }

    JayaBallardEmblemCastFromGraveyardEffect(final JayaBallardEmblemCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public JayaBallardEmblemCastFromGraveyardEffect copy() {
        return new JayaBallardEmblemCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(getTargetPointer().getFirst(game, source)) &&
            affectedControllerId.equals(source.getControllerId())
        ) {
            return true;
        }
        return false;
    }
}

class JayaBallardEmblemReplacementEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    JayaBallardEmblemReplacementEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
        staticText = "If a card cast this way would be put into your graveyard this turn, exile it instead";
    }

    JayaBallardEmblemReplacementEffect(final JayaBallardEmblemReplacementEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public JayaBallardEmblemReplacementEffect copy() {
        return new JayaBallardEmblemReplacementEffect(this);
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
