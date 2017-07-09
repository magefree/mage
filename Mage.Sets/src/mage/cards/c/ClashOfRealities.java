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
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class ClashOfRealities extends CardImpl {

    private static final FilterCreaturePermanent filterSpirit = new FilterCreaturePermanent("Spirit creature");
    private static final FilterCreaturePermanent filterNotSpirit = new FilterCreaturePermanent("non-Spirit creature");

    static {
        filterSpirit.add(new SubtypePredicate(SubType.SPIRIT));
        filterNotSpirit.add(Predicates.not(new SubtypePredicate(SubType.SPIRIT)));
    }

    public ClashOfRealities(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");


        // All Spirits have "When this permanent enters the battlefield, you may have it deal 3 damage to target non-Spirit creature."
        Ability ability1 = new ClashOfRealitiesTriggeredAbility(new DamageTargetEffect(3), "When this permanent enters the battlefield, ");
        ability1.addTarget(new TargetCreaturePermanent(filterNotSpirit));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(ability1, Duration.WhileOnBattlefield, filterSpirit, "All Spirits have \"When this permanent enters the battlefield, you may have it deal 3 damage to target non-Spirit creature.\"")));

        // Non-Spirit creatures have "When this creature enters the battlefield, you may have it deal 3 damage to target Spirit creature."
        Ability ability2 = new ClashOfRealitiesTriggeredAbility(new DamageTargetEffect(3), "When this creature enters the battlefield, ");
        ability2.addTarget(new TargetCreaturePermanent(filterSpirit));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(ability2, Duration.WhileOnBattlefield, filterNotSpirit, "Non-Spirit creatures have \"When this creature enters the battlefield, you may have it deal 3 damage to target Spirit creature.\"")));
    }

    public ClashOfRealities(final ClashOfRealities card) {
        super(card);
    }

    @Override
    public ClashOfRealities copy() {
        return new ClashOfRealities(this);
    }

    private static class ClashOfRealitiesTriggeredAbility extends ZoneChangeTriggeredAbility {

        public ClashOfRealitiesTriggeredAbility(Effect effect, String rule) {
            super(Zone.BATTLEFIELD, effect, rule, true);
        }

        public ClashOfRealitiesTriggeredAbility(ClashOfRealitiesTriggeredAbility ability) {
            super(ability);
        }


        @Override
        public ClashOfRealitiesTriggeredAbility copy() {
            return new ClashOfRealitiesTriggeredAbility(this);
        }
    }
}
