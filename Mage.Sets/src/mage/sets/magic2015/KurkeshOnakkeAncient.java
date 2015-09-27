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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class KurkeshOnakkeAncient extends CardImpl {

    public KurkeshOnakkeAncient(UUID ownerId) {
        super(ownerId, 153, "Kurkesh, Onakke Ancient", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "M15";
        this.supertype.add("Legendary");
        this.subtype.add("Ogre");
        this.subtype.add("Spirit");

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever you activate an ability of an artifact, if it isn't a mana ability, you may pay {R}.  If you do, copy that ability.  You may choose new targets for the copy.
        this.addAbility(new KurkeshOnakkeAncientTriggeredAbility());
    }

    public KurkeshOnakkeAncient(final KurkeshOnakkeAncient card) {
        super(card);
    }

    @Override
    public KurkeshOnakkeAncient copy() {
        return new KurkeshOnakkeAncient(this);
    }
}

class KurkeshOnakkeAncientTriggeredAbility extends TriggeredAbilityImpl {

    KurkeshOnakkeAncientTriggeredAbility() {
        super(Zone.BATTLEFIELD, new KurkeshOnakkeAncientEffect(), false);
    }

    KurkeshOnakkeAncientTriggeredAbility(final KurkeshOnakkeAncientTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KurkeshOnakkeAncientTriggeredAbility copy() {
        return new KurkeshOnakkeAncientTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId())) {
            Card source = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (source != null && source.getCardType().contains(CardType.ARTIFACT)) {
                StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
                if (!(stackAbility.getStackAbility() instanceof ManaAbility)) {
                    Effect effect = this.getEffects().get(0);
                    effect.setValue("stackAbility", stackAbility.getStackAbility());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you activate an ability of an artifact, if it isn't a mana ability" + super.getRule();
    }
}

class KurkeshOnakkeAncientEffect extends OneShotEffect {

    KurkeshOnakkeAncientEffect() {
        super(Outcome.Benefit);
        this.staticText = ", you may pay {R}. If you do, copy that ability. You may choose new targets for the copy";
    }

    KurkeshOnakkeAncientEffect(final KurkeshOnakkeAncientEffect effect) {
        super(effect);
    }

    @Override
    public KurkeshOnakkeAncientEffect copy() {
        return new KurkeshOnakkeAncientEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ColoredManaCost cost = new ColoredManaCost(ColoredManaSymbol.R);
        if (player != null) {
            if (player.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + "? If you do, copy that ability.  You may choose new targets for the copy.", source, game)) {
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)) {
                    Ability ability = (Ability) getValue("stackAbility");
                    Player controller = game.getPlayer(source.getControllerId());
                    Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                    if (ability != null && controller != null) {
                        Ability newAbility = ability.copy();
                        newAbility.newId();
                        game.getStack().push(new StackAbility(newAbility, source.getControllerId()));
                        if (newAbility.getTargets().size() > 0) {
                            if (controller.chooseUse(newAbility.getEffects().get(0).getOutcome(), "Choose new targets?", source, game)) {
                                newAbility.getTargets().clearChosen();
                                if (newAbility.getTargets().chooseTargets(newAbility.getEffects().get(0).getOutcome(), source.getControllerId(), newAbility, false, game) == false) {
                                    return false;
                                }
                            }
                        }
                        game.informPlayers(new StringBuilder(sourcePermanent.getName()).append(": ").append(controller.getLogName()).append(" copied activated ability").toString());
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }
}
