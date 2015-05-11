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
package mage.sets.futuresight;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author anonymous
 */
public class LymphSliver extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Sliver", "All Sliver creatures");

    public LymphSliver(UUID ownerId) {
        super(ownerId, 26, "Lymph Sliver", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.expansionSetCode = "FUT";
        this.subtype.add("Sliver");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // All Sliver creatures have absorb 1.
        Ability absorb = new SimpleStaticAbility(Zone.BATTLEFIELD, new SliverAbsorbEffect());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(absorb, Duration.WhileOnBattlefield, filter, "If a source would deal damage to a Sliver, prevent 1 of that damage")));
    }

    public LymphSliver(final LymphSliver card) {
        super(card);
    }

    @Override
    public LymphSliver copy() {
        return new LymphSliver(this);
    }
}

class SliverAbsorbEffect extends PreventionEffectImpl {
    public SliverAbsorbEffect() {
        super(Duration.WhileOnBattlefield, 1, false, false);
        this.staticText = "If a source would deal damage to a Sliver, prevent 1 of that damage";
    }

    public SliverAbsorbEffect(SliverAbsorbEffect effect) {
        super(effect);
    }

    @Override
    public SliverAbsorbEffect copy() {
        return new SliverAbsorbEffect(this);
    }
}