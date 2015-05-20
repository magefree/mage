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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
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
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class KheruLichLord extends CardImpl {

    public KheruLichLord(UUID ownerId) {
        super(ownerId, 182, "Kheru Lich Lord", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{G}{U}");
        this.expansionSetCode = "KTK";
        this.subtype.add("Zombie");
        this.subtype.add("Wizard");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you may pay {2}{B}. If you do, return a creature card at random from your graveyard to the battlefield. It gains flying, trample, and haste. Exile that card at the beginning of the next end step. If that card would leave the battlefield, exile it instead of putting it anywhere else.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new DoIfCostPaid(new KheruLichLordEffect(), new ManaCostsImpl("{2}{B}"), "Return creature card from your graveyard?"), TargetController.YOU, false));
    }

    public KheruLichLord(final KheruLichLord card) {
        super(card);
    }

    @Override
    public KheruLichLord copy() {
        return new KheruLichLord(this);
    }
}

class KheruLichLordEffect extends OneShotEffect {

    public KheruLichLordEffect() {
        super(Outcome.Benefit);
        this.staticText = "return a creature card at random from your graveyard to the battlefield. It gains flying, trample, and haste. Exile that card at the beginning of the next end step. If that card would leave the battlefield, exile it instead of putting it anywhere else";
    }

    public KheruLichLordEffect(final KheruLichLordEffect effect) {
        super(effect);
    }

    @Override
    public KheruLichLordEffect copy() {
        return new KheruLichLordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl();
            for (Card card : controller.getGraveyard().getCards(new FilterCreatureCard(), source.getSourceId(), source.getControllerId(), game)) {
                cards.add(card.getId());
            }

            if (cards.size() > 0) {
                Card card = cards.getRandom(game);
                controller.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId());
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    ContinuousEffect effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(permanent.getId()));
                    game.addEffect(effect, source);

                    effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(permanent.getId()));
                    game.addEffect(effect, source);

                    effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(permanent.getId()));
                    game.addEffect(effect, source);

                    ExileTargetEffect exileEffect = new ExileTargetEffect();
                    exileEffect.setTargetPointer(new FixedTarget(permanent.getId()));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    delayedAbility.setSourceId(source.getSourceId());
                    delayedAbility.setControllerId(source.getControllerId());
                    delayedAbility.setSourceObject(source.getSourceObject(game), game);
                    game.addDelayedTriggeredAbility(delayedAbility);

                    KheruLichLordReplacementEffect replacementEffect = new  KheruLichLordReplacementEffect();
                    replacementEffect.setTargetPointer(new FixedTarget(permanent.getId()));
                    game.addEffect(replacementEffect, source);

                }
            }
            return true;
        }


        return false;
    }
}

class KheruLichLordReplacementEffect extends ReplacementEffectImpl {

    KheruLichLordReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "If that card would leave the battlefield, exile it instead of putting it anywhere else";
    }

    KheruLichLordReplacementEffect(final KheruLichLordReplacementEffect effect) {
        super(effect);
    }

    @Override
    public KheruLichLordReplacementEffect copy() {
        return new KheruLichLordReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, game.getState().getZone(card.getId()), true);
            }
        }
        return true;
    }

    @Override    
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && event.getTargetId().equals(getTargetPointer().getFirst(game, source))
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
