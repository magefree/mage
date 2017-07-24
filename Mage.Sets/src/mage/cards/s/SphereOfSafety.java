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
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayManaAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class SphereOfSafety extends CardImpl {

    public SphereOfSafety(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{W}");

        // Creatures can't attack you or a planeswalker you control unless their controller pays {X} for each of those creatures, where X is the number of enchantments you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SphereOfSafetyPayManaToAttackAllEffect()));

    }

    public SphereOfSafety(final SphereOfSafety card) {
        super(card);
    }

    @Override
    public SphereOfSafety copy() {
        return new SphereOfSafety(this);
    }

}

class SphereOfSafetyPayManaToAttackAllEffect extends CantAttackYouUnlessPayManaAllEffect {

    SphereOfSafetyPayManaToAttackAllEffect() {
        super(null, true);
        staticText = "Creatures can't attack you or a planeswalker you control unless their controller pays {X} for each of those creatures, where X is the number of enchantments you control.";
    }

    SphereOfSafetyPayManaToAttackAllEffect(SphereOfSafetyPayManaToAttackAllEffect effect) {
        super(effect);
    }

    @Override
    public ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game) {
        int enchantments = game.getBattlefield().countAll(StaticFilters.FILTER_ENCHANTMENT_PERMANENT, source.getControllerId(), game);
        if (enchantments > 0) {
            return new ManaCostsImpl<>("{" + enchantments + '}');
        }
        return null;
    }

    @Override
    public SphereOfSafetyPayManaToAttackAllEffect copy() {
        return new SphereOfSafetyPayManaToAttackAllEffect(this);
    }

}
