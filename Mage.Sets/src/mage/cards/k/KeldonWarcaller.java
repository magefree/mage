
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class KeldonWarcaller extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Saga you control");

    static {
        filter.add(SubType.SAGA.getPredicate());
    }

    public KeldonWarcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Keldon Warcaller attacks, put a lore counter on target Saga you control.
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.LORE.createInstance()), false);
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);
    }

    private KeldonWarcaller(final KeldonWarcaller card) {
        super(card);
    }

    @Override
    public KeldonWarcaller copy() {
        return new KeldonWarcaller(this);
    }
}
