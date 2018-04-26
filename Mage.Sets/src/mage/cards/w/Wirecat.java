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
package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author Derpthemeus
 */
public class Wirecat extends CardImpl {

    public Wirecat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Wirecat can't attack or block if an enchantment is on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WirecatEffect()));
    }

    public Wirecat(final Wirecat card) {
        super(card);
    }

    @Override
    public Wirecat copy() {
        return new Wirecat(this);
    }

    static class WirecatEffect extends RestrictionEffect {

        public WirecatEffect() {
            super(Duration.WhileOnBattlefield);
            staticText = "{this} can't attack or block if an enchantment is on the battlefield";
        }

        public WirecatEffect(final WirecatEffect effect) {
            super(effect);
        }

        @Override
        public WirecatEffect copy() {
            return new WirecatEffect(this);
        }

        @Override
        public boolean canAttackCheckAfter(int numberOfAttackers, Ability source, Game game) {
            return false;
        }

        @Override
        public boolean canBlockCheckAfter(Ability source, Game game) {
            return false;
        }

        @Override
        public boolean applies(Permanent permanent, Ability source, Game game) {
            if (permanent.getId().equals(source.getSourceId())) {
                return game.getBattlefield().contains(StaticFilters.FILTER_ENCHANTMENT_PERMANENT, 1, game);
            }
            return false;
        }
    }
}
