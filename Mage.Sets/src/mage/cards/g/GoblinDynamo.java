
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 * @author BursegSardaukar
 */
public final class GoblinDynamo extends CardImpl {

    public GoblinDynamo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.MUTANT);
        
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {T}: Goblin Dynamo deals 1 damage to any target.
        Ability  ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        
        //{X}{R}, {T}, Sacrifice Goblin Dynamo: Goblin Dynamo deals X damage to any target.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,new DamageTargetEffect(ManacostVariableValue.REGULAR), new ManaCostsImpl<>("{X}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private GoblinDynamo(final GoblinDynamo card) {
        super(card);
    }

    @Override
    public GoblinDynamo copy() {
        return new GoblinDynamo(this);
    }
}
