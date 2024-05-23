package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExpandingOoze extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("modified creature you control");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public ExpandingOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {B}{G}: Adapt 1.
        this.addAbility(new AdaptAbility(1, "{B}{G}"));

        // Whenever Expanding Ooze attacks, put a +1/+1 counter on target modified creature you control.
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ExpandingOoze(final ExpandingOoze card) {
        super(card);
    }

    @Override
    public ExpandingOoze copy() {
        return new ExpandingOoze(this);
    }
}
