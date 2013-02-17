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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class GruulRagebeast extends CardImpl<GruulRagebeast> {

    final private static FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter2.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public GruulRagebeast(UUID ownerId) {
        super(ownerId, 170, "Gruul Ragebeast", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{R}{G}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Beast");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new CardIdPredicate(this.getId()));

        // Whenever Gruul Ragebeast or another creature enters the battlefield under your control, that creature fights target creature an opponent controls.
        Ability ability = new GruulRagebeastTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability);
    }

    public GruulRagebeast(final GruulRagebeast card) {
        super(card);
    }

    @Override
    public GruulRagebeast copy() {
        return new GruulRagebeast(this);
    }
}

class GruulRagebeastTriggeredAbility extends TriggeredAbilityImpl<GruulRagebeastTriggeredAbility> {

    GruulRagebeastTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new GruulRagebeastEffect(), false);
    }

    GruulRagebeastTriggeredAbility(final GruulRagebeastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GruulRagebeastTriggeredAbility copy() {
        return new GruulRagebeastTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            UUID targetId = event.getTargetId();
            Permanent permanent = game.getPermanent(targetId);
            if (permanent.getControllerId().equals(this.controllerId)
                    && permanent.getCardType().contains(CardType.CREATURE)
                    && (targetId.equals(this.getSourceId())
                    || !targetId.equals(this.getSourceId()))) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another creature enters the battlefield under your control, that creature fights target creature an opponent controls.";
    }
}

class GruulRagebeastEffect extends OneShotEffect<GruulRagebeastEffect> {

    GruulRagebeastEffect() {
        super(Constants.Outcome.Damage);
    }

    GruulRagebeastEffect(final GruulRagebeastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeredCreature = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (triggeredCreature != null
                && target != null
                && triggeredCreature.getCardType().contains(CardType.CREATURE)
                && target.getCardType().contains(CardType.CREATURE)) {
            triggeredCreature.damage(target.getPower().getValue(), target.getId(), game, true, false);
            target.damage(triggeredCreature.getPower().getValue(), triggeredCreature.getId(), game, true, false);
            return true;
        }
        return false;
    }

    @Override
    public GruulRagebeastEffect copy() {
        return new GruulRagebeastEffect(this);
    }
}
