package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PartnerVariantType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JoelResoluteSurvivor extends CardImpl {

    public JoelResoluteSurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever a creature token dies, put a +1/+1 counter on Joel and draw a card. This ability triggers only once each turn.
        Ability ability = new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, StaticFilters.FILTER_CREATURE_TOKEN
        ).setTriggersLimitEachTurn(1);
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

        // Partner--Survivors
        this.addAbility(PartnerVariantType.SURVIVORS.makeAbility());
    }

    private JoelResoluteSurvivor(final JoelResoluteSurvivor card) {
        super(card);
    }

    @Override
    public JoelResoluteSurvivor copy() {
        return new JoelResoluteSurvivor(this);
    }
}
