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
package mage.sets.starwars;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.NonCombatDamageWatcher;

/**
 *
 * @author Styxo
 */
public class SithMagic extends CardImpl {

    public SithMagic(UUID ownerId) {
        super(ownerId, 215, "Sith Magic", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{U}{B}{R}");
        this.expansionSetCode = "SWS";

        // <i>Hate</i> &mdash; At the beggining of each combat, if opponent lost life from a source other than combat damage this turn, you may return target card from a graveyard to the battlefield under your control. It gains lifelink and haste. Exile it at the beginning of the next end step or if it would leave the battlefield.
        TriggeredAbility triggeredAbility = new BeginningOfCombatTriggeredAbility(new SithMagicEffect(), TargetController.ANY, true);
        triggeredAbility.addEffect(new SithMagicReplacementEffect());
        Ability ability = new ConditionalTriggeredAbility(
                triggeredAbility,
                HateCondition.getInstance(),
                "<i>Hate</i> &mdash; At the beggining of each combat, if opponent lost life from a source other than combat damage this turn, you may return target card from a graveyard to the battlefield under your control. It gains lifelink and haste. Exile it at the beginning of the next end step or if it would leave the battlefield.");
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard()));
        this.addAbility(ability, new NonCombatDamageWatcher());
    }

    public SithMagic(final SithMagic card) {
        super(card);
    }

    @Override
    public SithMagic copy() {
        return new SithMagic(this);
    }
}

class SithMagicEffect extends OneShotEffect {

    public SithMagicEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return target card from a graveyard to the battlefield under your control. It gains lifelink and haste. Exile it at the beginning of the next end step or if it would leave the battlefield";
    }

    public SithMagicEffect(final SithMagicEffect effect) {
        super(effect);
    }

    @Override
    public SithMagicEffect copy() {
        return new SithMagicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null) {
            if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                Permanent creature = game.getPermanent(card.getId());
                if (creature != null) {
                    // gains haste
                    ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                    effect.setTargetPointer(new FixedTarget(creature, game));
                    game.addEffect(effect, source);
                    // gains lifelink
                    effect = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.Custom);
                    effect.setTargetPointer(new FixedTarget(creature, game));
                    game.addEffect(effect, source);
                    // Exile at begin of next end step
                    ExileTargetEffect exileEffect = new ExileTargetEffect(null, null, Zone.BATTLEFIELD);
                    exileEffect.setTargetPointer(new FixedTarget(creature, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
            }
            return true;
        }
        return false;
    }
}

class SithMagicReplacementEffect extends ReplacementEffectImpl {

    SithMagicReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "or if it would leave the battlefield";
    }

    SithMagicReplacementEffect(final SithMagicReplacementEffect effect) {
        super(effect);
    }

    @Override
    public SithMagicReplacementEffect copy() {
        return new SithMagicReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent != null && controller != null) {
            controller.moveCardToExileWithInfo(permanent, null, null, source.getSourceId(), game, Zone.BATTLEFIELD, true);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getFirstTarget())
                && ((ZoneChangeEvent) event).getFromZone().equals(Zone.BATTLEFIELD)
                && !((ZoneChangeEvent) event).getToZone().equals(Zone.EXILED)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
