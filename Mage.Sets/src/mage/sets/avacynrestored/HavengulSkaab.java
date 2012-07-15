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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author noxx
 */
public class HavengulSkaab extends CardImpl<HavengulSkaab> {

    public HavengulSkaab(UUID ownerId) {
        super(ownerId, 60, "Havengul Skaab", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{5}{U}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Zombie");
        this.subtype.add("Horror");

        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever Havengul Skaab attacks, return another creature you control to its owner's hand.
        this.addAbility(new HavengulSkaabAbility());
    }

    public HavengulSkaab(final HavengulSkaab card) {
        super(card);
    }

    @Override
    public HavengulSkaab copy() {
        return new HavengulSkaab(this);
    }
}

class HavengulSkaabAbility extends TriggeredAbilityImpl<HavengulSkaabAbility> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(new AnotherPredicate());
    }

    public HavengulSkaabAbility() {
        super(Constants.Zone.BATTLEFIELD, new DestroyTargetEffect());
        this.addEffect(new ReturnToHandTargetEffect());
    }

    public HavengulSkaabAbility(final HavengulSkaabAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
            TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent(1, 1, filter, false, true);
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, return another creature you control to its owner's hand.";
    }

    @Override
    public HavengulSkaabAbility copy() {
        return new HavengulSkaabAbility(this);
    }
}
