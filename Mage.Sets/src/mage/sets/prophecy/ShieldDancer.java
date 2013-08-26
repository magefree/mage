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
package mage.sets.prophecy;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author LevelX2
 */
public class ShieldDancer extends CardImpl<ShieldDancer> {

    public ShieldDancer(UUID ownerId) {
        super(ownerId, 23, "Shield Dancer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "PCY";
        this.subtype.add("Human");
        this.subtype.add("Rebel");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}{W}: The next time target attacking creature would deal combat damage to Shield Dancer this turn, that creature deals that damage to itself instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShieldDancerRedirectionEffect(), new ManaCostsImpl("{2}{W}"));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    public ShieldDancer(final ShieldDancer card) {
        super(card);
    }

    @Override
    public ShieldDancer copy() {
        return new ShieldDancer(this);
    }
}

class ShieldDancerRedirectionEffect extends RedirectionEffect<ShieldDancerRedirectionEffect> {

    public ShieldDancerRedirectionEffect() {
        super(Duration.EndOfTurn);
        staticText = "The next time target attacking creature would deal combat damage to {this} this turn, that creature deals that damage to itself instead";
    }

    public ShieldDancerRedirectionEffect(final ShieldDancerRedirectionEffect effect) {
        super(effect);
    }

    @Override
    public ShieldDancerRedirectionEffect copy() {
        return new ShieldDancerRedirectionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.DAMAGE_CREATURE
                && event.getTargetId().equals(source.getSourceId())
                && event.getSourceId().equals(source.getTargets().get(0).getFirstTarget())) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (damageEvent.isCombatDamage()) {
                TargetPermanent target = new TargetPermanent();
                target.add(source.getTargets().get(0).getFirstTarget(), game);
                redirectTarget = target;
            }
            return true;
        }
        return false;
    }

}
