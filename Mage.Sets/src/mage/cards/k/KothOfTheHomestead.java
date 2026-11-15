package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class KothOfTheHomestead extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Plains");

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    public KothOfTheHomestead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Landfall -- Whenever a land you control enters, you gain 1 life.
        this.addAbility(new LandfallAbility(new GainLifeEffect(1)));

        // Whenever a Plains you control enters, put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
           new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
           filter
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KothOfTheHomestead(final KothOfTheHomestead card) {
        super(card);
    }

    @Override
    public KothOfTheHomestead copy() {
        return new KothOfTheHomestead(this);
    }
}
