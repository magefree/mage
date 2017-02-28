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

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Derpthemeus
 */
public class ScarredPuma extends CardImpl {

    public ScarredPuma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add("Cat");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Scarred Puma can't attack unless a black or green creature also attacks.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ScarredPumaEffect()));
    }

    public ScarredPuma(final ScarredPuma card) {
        super(card);
    }

    @Override
    public ScarredPuma copy() {
        return new ScarredPuma(this);
    }

    static class ScarredPumaEffect extends RestrictionEffect {

        private final FilterAttackingCreature filter = new FilterAttackingCreature();

        public ScarredPumaEffect() {
            super(Duration.WhileOnBattlefield);
            staticText = "{this} can't attack unless a black or green creature also attacks";
        }

        public ScarredPumaEffect(final ScarredPumaEffect effect) {
            super(effect);
        }

        @Override
        public ScarredPumaEffect copy() {
            return new ScarredPumaEffect(this);
        }

        @Override
        public boolean canAttackCheckAfter(int numberOfAttackers, Ability source, Game game) {
            return false;
        }

        @Override
        public boolean applies(Permanent permanent, Ability source, Game game) {
            if (permanent.getId().equals(source.getSourceId())) {
                for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                    //excludes itself (http://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=23067)
                    if (!Objects.equals(creature.getId(), source.getSourceId())) {
                        ObjectColor color = creature.getColor(game);
                        if (color.isBlack() || color.isGreen()) {
                            return false;
                        }
                    }
                }
                return true;
            }
            return false;
        }
    }
}
