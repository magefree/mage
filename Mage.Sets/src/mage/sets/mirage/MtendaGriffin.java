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
package mage.sets.mirage;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public class MtendaGriffin extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("Griffin card from your graveyard");

    static {
        filter.add(new SubtypePredicate("Griffin"));
}

    public MtendaGriffin(UUID ownerId) {
        super(ownerId, 232, "Mtenda Griffin", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "MIR";
        this.subtype.add("Griffin");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {W}, {tap}: Return Mtenda Griffin to its owner's hand and return target Griffin card from your graveyard to your hand. Activate this ability only during your upkeep.
        Effect effect = new ReturnToHandSourceEffect(true);
        effect.setText("Return Mtenda Griffin to its owner's hand");        
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, 
               effect, new ManaCostsImpl("{W}"), new IsStepCondition(PhaseStep.UPKEEP));
        effect = new ReturnToHandTargetEffect();
        ability.addCost(new TapSourceCost());
        effect.setText("and return target Griffin card from your graveyard to your hand");
        ability.addEffect(effect);
        Target target = new TargetCardInYourGraveyard(filter);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public MtendaGriffin(final MtendaGriffin card) {
        super(card);
    }

    @Override
    public MtendaGriffin copy() {
        return new MtendaGriffin(this);
    }
}
