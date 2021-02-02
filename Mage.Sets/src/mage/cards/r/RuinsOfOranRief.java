
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RuinsOfOranRief extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("colorless creature that entered the battlefield this turn");

    static {
        filter.add(ColorlessPredicate.instance);
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public RuinsOfOranRief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Ruins of Oran-Rief enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {T}: Put a +1/+1 counter on target colorless creature that entered the battlefield this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));

        this.addAbility(ability);
    }

    private RuinsOfOranRief(final RuinsOfOranRief card) {
        super(card);
    }

    @Override
    public RuinsOfOranRief copy() {
        return new RuinsOfOranRief(this);
    }
}
