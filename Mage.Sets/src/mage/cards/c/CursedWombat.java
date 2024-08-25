package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author grimreap124
 */
public final class CursedWombat extends CardImpl {

    public CursedWombat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{B}{G}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.WOMBAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}{B}{G}: Adapt 2.
        this.addAbility(new AdaptAbility(2, "{2}{B}{G}"));

        // Permanents you control have "Whenever one or more +1/+1 counters are put on this permanent, put an additional +1/+1 counter on it. This ability triggers only once each turn."
        Ability p1p1Ability = new OneOrMoreCountersAddedTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("put an additional +1/+1 counter on it"))
                .setTriggerPhrase("Whenever one or more +1/+1 counters are put on this permanent, ")
                .setTriggersLimitEachTurn(1);

        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(p1p1Ability,
                Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENTS, false)));

    }

    private CursedWombat(final CursedWombat card) {
        super(card);
    }

    @Override
    public CursedWombat copy() {
        return new CursedWombat(this);
    }
}