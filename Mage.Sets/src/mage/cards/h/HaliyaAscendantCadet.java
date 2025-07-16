package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HaliyaAscendantCadet extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creatures you control with +1/+1 counters on them");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public HaliyaAscendantCadet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Haliya enters or attacks, put a +1/+1 counter on target creature you control.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever one or more creatures you control with +1/+1 counters on them deal combat damage to a player, draw a card.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter
        ));
    }

    private HaliyaAscendantCadet(final HaliyaAscendantCadet card) {
        super(card);
    }

    @Override
    public HaliyaAscendantCadet copy() {
        return new HaliyaAscendantCadet(this);
    }
}
