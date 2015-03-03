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
package mage.sets.vintagemasters;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DethroneAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class MarchesaTheBlackRose extends CardImpl {

    public MarchesaTheBlackRose(UUID ownerId) {
        super(ownerId, 256, "Marchesa, the Black Rose", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");
        this.expansionSetCode = "VMA";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Dethrone
        this.addAbility(new DethroneAbility());
        
        // Other creatures you control have dethrone.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(new DethroneAbility(), Duration.WhileOnBattlefield, new FilterCreaturePermanent(), true)));

        // Whenever a creature you control with a +1/+1 counter on it dies, return that card to the battlefield under your control at the beginning of the next end step.
        this.addAbility(new MarchesaTheBlackRoseTriggeredAbility());

    }

    public MarchesaTheBlackRose(final MarchesaTheBlackRose card) {
        super(card);
    }

    @Override
    public MarchesaTheBlackRose copy() {
        return new MarchesaTheBlackRose(this);
    }
}

class MarchesaTheBlackRoseTriggeredAbility extends TriggeredAbilityImpl {

    public MarchesaTheBlackRoseTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MarchesaTheBlackRoseEffect());
    }

    public MarchesaTheBlackRoseTriggeredAbility(final MarchesaTheBlackRoseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MarchesaTheBlackRoseTriggeredAbility copy() {
        return new MarchesaTheBlackRoseTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent != null &&
                    permanent.getControllerId().equals(this.getControllerId())
                    && permanent.getCardType().contains(CardType.CREATURE)
                    && permanent.getCounters().getCount(CounterType.P1P1) > 0) {
                for (Effect effect: this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(permanent.getId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control with a +1/+1 counter on it dies, " + super.getRule();
    }
}

class MarchesaTheBlackRoseEffect extends OneShotEffect {

    MarchesaTheBlackRoseEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return that card to the battlefield under your control at the beginning of the next end step.";
    }

    MarchesaTheBlackRoseEffect(final MarchesaTheBlackRoseEffect effect) {
        super(effect);
    }

    @Override
    public MarchesaTheBlackRoseEffect copy() {
        return new MarchesaTheBlackRoseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null) {
            Effect effect = new ReturnToBattlefieldUnderYourControlTargetEffect();
            effect.setText("return that card to the battlefield under your control at the beginning of the next end step");
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            delayedAbility.setSourceObject(source.getSourceObject(game));
            delayedAbility.getEffects().get(0).setTargetPointer(new FixedTarget(card.getId()));
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }
        return false;
    }
}
