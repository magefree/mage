package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MageTowerReferee extends CardImpl {

    public MageTowerReferee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a multicolored spell, put a +1/+1 counter on this creature.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_SPELL_A_MULTICOLORED, false
        ));
    }

    private MageTowerReferee(final MageTowerReferee card) {
        super(card);
    }

    @Override
    public MageTowerReferee copy() {
        return new MageTowerReferee(this);
    }
}
