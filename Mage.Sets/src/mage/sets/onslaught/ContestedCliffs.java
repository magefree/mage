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
package mage.sets.onslaught;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ContestedCliffs extends CardImpl<ContestedCliffs> {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("Beast creature you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature an opponent controls");
    static {
        filter1.add(new ControllerPredicate(TargetController.YOU));
        filter1.add(new SubtypePredicate("Beast"));
        filter2.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public ContestedCliffs(UUID ownerId) {
        super(ownerId, 314, "Contested Cliffs", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ONS";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // {R}{G}, {tap}: Choose target Beast creature you control and target creature an opponent controls. Those creatures fight each other.
        Effect effect = new FightTargetsEffect();
        effect.setText("Choose target Beast creature you control and target creature an opponent controls. Those creatures fight each other");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{R}{G}"));
        ability.addCost(new TapSourceCost());
        Target target1 = new TargetCreaturePermanent(filter1);
        target1.setRequired(true);
        ability.addTarget(target1);
        Target target2 = new TargetCreaturePermanent(filter2);
        target2.setRequired(true);
        ability.addTarget(target2);
        this.addAbility(ability);
        
    }

    public ContestedCliffs(final ContestedCliffs card) {
        super(card);
    }

    @Override
    public ContestedCliffs copy() {
        return new ContestedCliffs(this);
    }
}
