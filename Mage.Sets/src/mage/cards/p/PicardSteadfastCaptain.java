package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class PicardSteadfastCaptain extends CardImpl {

    public PicardSteadfastCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Picard enters or attacks, put two +1/+1 counters on another target creature you control.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(2))
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private PicardSteadfastCaptain(final PicardSteadfastCaptain card) {
        super(card);
    }

    @Override
    public PicardSteadfastCaptain copy() {
        return new PicardSteadfastCaptain(this);
    }
}
