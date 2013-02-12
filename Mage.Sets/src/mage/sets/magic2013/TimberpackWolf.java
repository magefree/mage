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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class TimberpackWolf extends CardImpl<TimberpackWolf> {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creature you control named Timberpack Wolf");

    static {
        filter.add(new NamePredicate("Timberpack Wolf"));
        filter.add(new ControllerPredicate(Constants.TargetController.YOU));
    }

    public TimberpackWolf(UUID ownerId) {
        super(ownerId, 194, "Timberpack Wolf", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "M13";
        this.subtype.add("Wolf");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Timberpack Wolf gets +1/+1 for each other creature you control named Timberpack Wolf.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new TimberpackWolfEffect()));
    }

    public TimberpackWolf(final TimberpackWolf card) {
        super(card);
    }

    @Override
    public TimberpackWolf copy() {
        return new TimberpackWolf(this);
    }


    class TimberpackWolfEffect extends ContinuousEffectImpl<TimberpackWolfEffect> {

        public TimberpackWolfEffect() {
            super(Constants.Duration.WhileOnBattlefield, Constants.Layer.PTChangingEffects_7, Constants.SubLayer.ModifyPT_7c, Constants.Outcome.BoostCreature);
            staticText = "{this} gets +1/+1 for each other creature you control named Timberpack Wolf";
        }

        public TimberpackWolfEffect(final TimberpackWolfEffect effect) {
            super(effect);
        }

        @Override
        public TimberpackWolfEffect copy() {
            return new TimberpackWolfEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            int count = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) - 1;
            if (count > 0) {
                Permanent target = (Permanent) game.getPermanent(source.getSourceId());
                if (target != null) {
                    target.addPower(count);
                    target.addToughness(count);
                    return true;
                }
            }
            return false;
        }
    }
}




