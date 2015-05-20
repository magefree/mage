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
package mage.sets.commander2014;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class DulcetSirens extends CardImpl {

    public DulcetSirens(UUID ownerId) {
        super(ownerId, 14, "Dulcet Sirens", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "C14";
        this.subtype.add("Siren");

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {U}, {T}: Target creature attacks target opponent this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DulcetSirensForceAttackEffect(Duration.EndOfTurn), new ManaCostsImpl("{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addTarget(new TargetOpponent());        
        this.addAbility(ability);


        // Morph {U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{U}")));
    }

    public DulcetSirens(final DulcetSirens card) {
        super(card);
    }

    @Override
    public DulcetSirens copy() {
        return new DulcetSirens(this);
    }
}

class DulcetSirensForceAttackEffect extends RequirementEffect {

    public DulcetSirensForceAttackEffect(Duration duration) {
        super(duration);
        staticText = "Target creature attacks target opponent this turn if able";
    }

    public DulcetSirensForceAttackEffect(final DulcetSirensForceAttackEffect effect) {
        super(effect);
    }

    @Override
    public DulcetSirensForceAttackEffect copy() {
        return new DulcetSirensForceAttackEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (this.getTargetPointer().getTargets(game, source).contains(permanent.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        Target target = source.getTargets().get(1);
        if (target != null) {
            return target.getFirstTarget();
        }
        return null;
    }

}
