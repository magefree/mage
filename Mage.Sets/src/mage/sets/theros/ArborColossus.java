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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ArborColossus extends CardImpl<ArborColossus> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying an opponent controls");
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public ArborColossus(UUID ownerId) {
        super(ownerId, 150, "Arbor Colossus", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{G}{G}");
        this.expansionSetCode = "THS";
        this.subtype.add("Giant");

        this.color.setGreen(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // {3}{G}{G}{G}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{3}{G}{G}{G}", 3));
        // When Arbor Colossus becomes monstrous, destroy target creature with flying an opponent controls.
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(new DestroyTargetEffect());
        Target target = new TargetCreaturePermanent(filter);
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public ArborColossus(final ArborColossus card) {
        super(card);
    }

    @Override
    public ArborColossus copy() {
        return new ArborColossus(this);
    }
}
