
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class BloodChinFanatic extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another Warrior creature");
    
    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.WARRIOR.getPredicate());
    }

    public BloodChinFanatic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.ORC, SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{B}, Sacrifice another Warrior creature: Target player loses X life and you gain X life, where X is the sacrificed creature's power.
        Effect effect = new LoseLifeTargetEffect(SacrificeCostCreaturesPower.instance);
        effect.setText("Target player loses X life");
        Effect effect2 = new GainLifeEffect(SacrificeCostCreaturesPower.instance);
        effect2.setText("and you gain X life, where X is the sacrificed creature's power");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}{B}"));
        ability.addEffect(effect2);
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        
    }

    private BloodChinFanatic(final BloodChinFanatic card) {
        super(card);
    }

    @Override
    public BloodChinFanatic copy() {
        return new BloodChinFanatic(this);
    }
}
