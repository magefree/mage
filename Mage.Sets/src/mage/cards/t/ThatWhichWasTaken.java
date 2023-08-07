
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;



/**
 * @author LevelX2
 */
public final class ThatWhichWasTaken extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent other than That Which Was Taken");

    private static final FilterPermanent filterIndestructible = new FilterPermanent("Each permanent with a divinity counter on it");

    static {
        filter.add(AnotherPredicate.instance);
        filterIndestructible.add(CounterType.DIVINITY.getPredicate());
    }

    public ThatWhichWasTaken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");
        this.supertype.add(SuperType.LEGENDARY);

        // {4}, {T}: Put a divinity counter on target permanent other than That Which Was Taken.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.DIVINITY.createInstance()), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Each permanent with a divinity counter on it has indestructible.
        Effect effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filterIndestructible, false);
        effect.setText("Each permanent with a divinity counter on it has indestructible");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                effect));

    }

    private ThatWhichWasTaken(final ThatWhichWasTaken card) {
        super(card);
    }

    @Override
    public ThatWhichWasTaken copy() {
        return new ThatWhichWasTaken(this);
    }
}
