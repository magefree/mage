package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author AhmadYProjects
 */
public final class CinderslashRavager extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("permanent you control with oil counters on it");

    static {
        filter.add(CounterType.OIL.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public CinderslashRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This spell costs {1} less to cast for each permanent you control with oil counters on it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Cinderslash Ravager enters the battlefield, it deals 1 damage to each creature your opponents control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamageAllEffect(
                1, "it", StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        )));
    }

    private CinderslashRavager(final CinderslashRavager card) {
        super(card);
    }

    @Override
    public CinderslashRavager copy() {
        return new CinderslashRavager(this);
    }
}
