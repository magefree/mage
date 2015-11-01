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
package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author nantuko
 */
public class MimicVat extends CardImpl {

    public MimicVat(UUID ownerId) {
        super(ownerId, 175, "Mimic Vat", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "SOM";

        // Imprint - Whenever a nontoken creature dies, you may exile that card. If you do, return each other card exiled with Mimic Vat to its owner's graveyard.
        this.addAbility(new MimicVatTriggeredAbility());

        // {3}, {tap}: Put a token onto the battlefield that's a copy of the exiled card. It gains haste. Exile it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MimicVatCreateTokenEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public MimicVat(final MimicVat card) {
        super(card);
    }

    @Override
    public MimicVat copy() {
        return new MimicVat(this);
    }
}

class MimicVatTriggeredAbility extends TriggeredAbilityImpl {

    MimicVatTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MimicVatEffect(), true);
    }

    MimicVatTriggeredAbility(MimicVatTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MimicVatTriggeredAbility copy() {
        return new MimicVatTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // make sure card is on battlefield
        UUID sourceCardId = getSourceId();
        if (game.getPermanent(sourceCardId) == null) {
            // or it is being removed
            if (game.getLastKnownInformation(sourceCardId, Zone.BATTLEFIELD) == null) {
                return false;
            }
        }

        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent permanent = zEvent.getTarget();

        if (permanent != null
                && zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getFromZone() == Zone.BATTLEFIELD
                && !(permanent instanceof PermanentToken)
                && permanent.getCardType().contains(CardType.CREATURE)) {

            getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken creature dies, you may exile that card. If you do, return each other card exiled with {this} to its owner's graveyard.";
    }
}

class MimicVatEffect extends OneShotEffect {

    public MimicVatEffect() {
        super(Outcome.Benefit);
        staticText = "exile that card";
    }

    public MimicVatEffect(MimicVatEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || permanent == null) {
            return false;
        }
        // return older cards to graveyard
        for (UUID imprinted : permanent.getImprinted()) {
            Card card = game.getCard(imprinted);
            controller.moveCards(card, Zone.EXILED, Zone.GRAVEYARD, source, game);
        }
        permanent.clearImprinted(game);

        // Imprint a new one
        UUID target = targetPointer.getFirst(game, source);
        if (target != null) {
            Card card = game.getCard(target);
            card.moveToExile(getId(), "Mimic Vat (Imprint)", source.getSourceId(), game);
            permanent.imprint(card.getId(), game);
        }

        return true;
    }

    @Override
    public MimicVatEffect copy() {
        return new MimicVatEffect(this);
    }

}

class MimicVatCreateTokenEffect extends OneShotEffect {

    public MimicVatCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a token onto the battlefield that's a copy of the exiled card. It gains haste. Exile it at the beginning of the next end step";
    }

    public MimicVatCreateTokenEffect(final MimicVatCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public MimicVatCreateTokenEffect copy() {
        return new MimicVatCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }

        if (permanent.getImprinted().size() > 0) {
            Card card = game.getCard(permanent.getImprinted().get(0));
            if (card != null) {
                PutTokenOntoBattlefieldCopyTargetEffect effect = new PutTokenOntoBattlefieldCopyTargetEffect(source.getControllerId(), null, true);
                effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
                effect.apply(game, source);
                for (Permanent addedToken : effect.getAddedPermanent()) {
                    ExileTargetEffect exileEffect = new ExileTargetEffect();
                    exileEffect.setTargetPointer(new FixedTarget(addedToken, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    delayedAbility.setSourceId(source.getSourceId());
                    delayedAbility.setControllerId(source.getControllerId());
                    delayedAbility.setSourceObject(source.getSourceObject(game), game);
                    game.addDelayedTriggeredAbility(delayedAbility);
                }

                return true;
            }
        }

        return false;
    }

}
