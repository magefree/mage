
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class MundasVanguard extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an untapped Ally you control");

    static {
        filter.add(SubType.ALLY.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public MundasVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.KNIGHT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: Put a +1/+1 counter on each creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent()),
                new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.setAbilityWord(AbilityWord.COHORT);
        this.addAbility(ability);
    }

    private MundasVanguard(final MundasVanguard card) {
        super(card);
    }

    @Override
    public MundasVanguard copy() {
        return new MundasVanguard(this);
    }
}
