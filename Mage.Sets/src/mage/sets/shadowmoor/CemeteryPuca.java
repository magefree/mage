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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class CemeteryPuca extends CardImpl {

    public CemeteryPuca(UUID ownerId) {
        super(ownerId, 158, "Cemetery Puca", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U/B}{U/B}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Shapeshifter");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever a creature dies, you may pay {1}. If you do, Cemetery Puca becomes a copy of that creature and gains this ability.
        this.addAbility(new DiesCreatureTriggeredAbility(new DoIfCostPaid(new CemeteryPucaEffect(), new ManaCostsImpl("{1}")), false, new FilterCreaturePermanent("a creature"), true));

    }

    public CemeteryPuca(final CemeteryPuca card) {
        super(card);
    }

    @Override
    public CemeteryPuca copy() {
        return new CemeteryPuca(this);
    }
}

class CemeteryPucaEffect extends OneShotEffect {

    public CemeteryPucaEffect() {
        super(Outcome.Copy);
        staticText = " {this} becomes a copy of that creature and gains this ability";
    }

    public CemeteryPucaEffect(final CemeteryPucaEffect effect) {
        super(effect);
    }

    @Override
    public CemeteryPucaEffect copy() {
        return new CemeteryPucaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent copyToCreature = game.getPermanent(source.getSourceId());
        if (copyToCreature != null) {
            Permanent copyFromCreature = (Permanent) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.BATTLEFIELD);
            if (copyFromCreature != null) {
                game.copyPermanent(Duration.WhileOnBattlefield, copyFromCreature, copyToCreature, source, new EmptyApplyToPermanent());
                ContinuousEffect effect = new GainAbilityTargetEffect(new DiesCreatureTriggeredAbility(new DoIfCostPaid(new CemeteryPucaEffect(), new ManaCostsImpl("{1}")), false, new FilterCreaturePermanent("a creature"), true), Duration.WhileOnBattlefield);
                effect.setTargetPointer(new FixedTarget(copyToCreature.getId()));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}
