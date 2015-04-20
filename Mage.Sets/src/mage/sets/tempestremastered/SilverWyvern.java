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
package mage.sets.tempestremastered;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetStackObject;
import mage.target.Targets;

/**
 *
 * @author LevelX2
 */
public class SilverWyvern extends CardImpl {
    
    public SilverWyvern(UUID ownerId) {
        super(ownerId, 68, "Silver Wyvern", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "TPR";
        this.subtype.add("Drake");
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {U}: Change the target of target spell or ability that targets only Silver Wyvern. The new target must be a creature.
        Effect effect = new ChooseNewTargetsTargetEffect(true, true);
        effect.setText("Change the target of target spell or ability that targets only {this}. The new target must be a creature");        
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{U}"));
        FilterStackObject filter = new FilterStackObject();
        filter.add(new TargetsOnlySourcePredicate(getId()));
        ability.addTarget(new TargetStackObject(filter));
        this.addAbility(ability);
        
    }

    public SilverWyvern(final SilverWyvern card) {
        super(card);
    }

    @Override
    public SilverWyvern copy() {
        return new SilverWyvern(this);
    }
}

class TargetsOnlySourcePredicate implements Predicate<MageObject> {

    private final UUID sourceId;

    public TargetsOnlySourcePredicate(UUID sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        StackObject stackObject = game.getStack().getStackObject(input.getId());
        if (stackObject != null) {
            Targets spellTargets = stackObject.getStackAbility().getTargets();
            int numberOfTargets = 0;
            for (Target target : spellTargets) {
                if (target.getFirstTarget() == null || !target.getFirstTarget().toString().equals(sourceId.toString())) { // UUID != UUID does not work - it's always false
                    return false;
                }
                numberOfTargets += target.getTargets().size();
            }
            if (numberOfTargets == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "target spell or ability that targets only source";
    }
}
