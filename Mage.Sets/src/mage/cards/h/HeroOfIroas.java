package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HeroOfIroas extends CardImpl {

    private static final FilterCard filter = new FilterCard("Aura spells");

    static {
        filter.add(SubType.AURA.getPredicate());
    }

    public HeroOfIroas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Aura spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // <i>Heroic</i> &mdash; Whenever you cast a spell that targets Hero of Iroas, put a +1/+1 counter on Hero of Iroas.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private HeroOfIroas(final HeroOfIroas card) {
        super(card);
    }

    @Override
    public HeroOfIroas copy() {
        return new HeroOfIroas(this);
    }
}
