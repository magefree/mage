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
package mage.sets.commander2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetDiscard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class DarettiScrapSavant extends CardImpl {

    public DarettiScrapSavant(UUID ownerId) {
        super(ownerId, 33, "Daretti, Scrap Savant", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{R}");
        this.expansionSetCode = "C14";
        this.subtype.add("Daretti");


        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), false));

        // +2: Discard up to two cards, then draw that many cards.
        this.addAbility(new LoyaltyAbility(new DarettiDiscardDrawEffect(), 2));

        // -2: Sacrifice an artifact. If you do, return target artifact card from your graveyard to the battlefield.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new DarettiSacrificeEffect(), -2);
        loyaltyAbility.addTarget(new TargetCardInYourGraveyard(new FilterArtifactCard("artifact card from your graveyard")));
        this.addAbility(loyaltyAbility);

        // -10: You get an emblem with "Whenever an artifact is put into your graveyard from the battlefield, return that card to the battlefield at the beginning of the next end step."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new DarettiScrapSavantEmblem()), -10));

        // Daretti, Scrap Savant can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    public DarettiScrapSavant(final DarettiScrapSavant card) {
        super(card);
    }

    @Override
    public DarettiScrapSavant copy() {
        return new DarettiScrapSavant(this);
    }
}

class DarettiDiscardDrawEffect extends OneShotEffect {

    public DarettiDiscardDrawEffect() {
        super(Outcome.Detriment);
        this.staticText = "Discard up to two cards, then draw that many cards";
    }

    public DarettiDiscardDrawEffect(final DarettiDiscardDrawEffect effect) {
        super(effect);
    }

    @Override
    public DarettiDiscardDrawEffect copy() {
        return new DarettiDiscardDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetDiscard target = new TargetDiscard(0, 2, new FilterCard(), controller.getId());
            target.choose(outcome, controller.getId(), source.getSourceId(), game);
            int count = 0;
            for (UUID cardId: target.getTargets()) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    controller.discard(card, source, game);
                    count++;
                }
            }
            controller.drawCards(count, game);
            return true;
        }
        return false;
    }
}

class DarettiSacrificeEffect extends OneShotEffect {

    public DarettiSacrificeEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Sacrifice an artifact. If you do, return target artifact card from your graveyard to the battlefield";
    }

    public DarettiSacrificeEffect(final DarettiSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public DarettiSacrificeEffect copy() {
        return new DarettiSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetControlledPermanent(1,1,new FilterControlledArtifactPermanent(), true);
            if (target.canChoose(source.getSourceId(), controller.getId(), game) &&
                    controller.chooseTarget(outcome, target, source, game)) {
                Permanent artifact = game.getPermanent(target.getFirstTarget());
                if (artifact != null && artifact.sacrifice(source.getSourceId(), game)) {
                    Card card = game.getCard(getTargetPointer().getFirst(game, source));
                    if (card != null) {
                        return controller.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId());
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class DarettiScrapSavantEmblem extends Emblem {
    // You get an emblem with "Whenever an artifact is put into your graveyard from the battlefield, return that card to the battlefield at the beginning of the next end step."
    public DarettiScrapSavantEmblem() {
        this.setName("Emblem - Daretti");
        this.getAbilities().add(new DarettiScrapSavantTriggeredAbility());
    }
}

class DarettiScrapSavantTriggeredAbility extends TriggeredAbilityImpl {

    DarettiScrapSavantTriggeredAbility() {
        super(Zone.COMMAND, new DarettiScrapSavantEffect(), false);
    }

    DarettiScrapSavantTriggeredAbility(final DarettiScrapSavantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DarettiScrapSavantTriggeredAbility copy() {
        return new DarettiScrapSavantTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD &&
                zEvent.getFromZone() == Zone.BATTLEFIELD &&
                zEvent.getTarget().getCardType().contains(CardType.ARTIFACT) &&
                zEvent.getTarget().getOwnerId().equals(this.controllerId)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(zEvent.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an artifact is put into your graveyard from the battlefield, " + super.getRule();
    }
}

class DarettiScrapSavantEffect extends OneShotEffect {

    DarettiScrapSavantEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return that card to the battlefield at the beginning of the next end step";
    }

    DarettiScrapSavantEffect(final DarettiScrapSavantEffect effect) {
        super(effect);
    }

    @Override
    public DarettiScrapSavantEffect copy() {
        return new DarettiScrapSavantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
            effect.setTargetPointer(new FixedTarget(card.getId()));
            effect.setText("return that card to the battlefield at the beginning of the next end step");
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(Zone.COMMAND, effect, TargetController.ANY);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            delayedAbility.setSourceObject(source.getSourceObject(game), game);
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }
        return false;
    }
}