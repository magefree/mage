
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.target.TargetPermanent;
/**
 *
 * @author Saga
 */
public final class KindredBoon extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filterDivinity = new FilterControlledCreaturePermanent("Each creature you control with a divinity counter on it");
    static {
        filterDivinity.add(CounterType.DIVINITY.getPredicate());
    }

    public KindredBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // As Kindred Boon enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.AddAbility)));
        
        // {1}{W}: Put a divinity counter on target creature you control of the chosen type.
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control of the chosen type");
        filter.add(ChosenSubtypePredicate.TRUE);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.DIVINITY.createInstance()), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
        
        // Each creature you control with a divinity counter on it has indestructible.
        Effect effect = new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filterDivinity);
        effect.setText("Each creature you control with a divinity counter on it has indestructible");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private KindredBoon(final KindredBoon card) {
        super(card);
    }

    @Override
    public KindredBoon copy() {
        return new KindredBoon(this);
    }
}
