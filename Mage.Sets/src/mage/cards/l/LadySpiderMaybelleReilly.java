package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class LadySpiderMaybelleReilly extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.SPIDER);

    public LadySpiderMaybelleReilly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever you discard a card, put a +1/+1 counter on target Spider you control.
        Ability ability = new DiscardCardControllerTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private LadySpiderMaybelleReilly(final LadySpiderMaybelleReilly card) {
        super(card);
    }

    @Override
    public LadySpiderMaybelleReilly copy() {
        return new LadySpiderMaybelleReilly(this);
    }
}
