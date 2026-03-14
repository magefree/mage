package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZukoSeekingHonor extends CardImpl {

    public ZukoSeekingHonor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Firebending 1
        this.addAbility(new FirebendingAbility(1));

        // Whenever you cast a noncreature spell, Zuko gains first strike until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // Whenever Zuko deals combat damage to a player, put a +1/+1 counter on him.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)).setText("put a +1/+1 counter on him")
        ));
    }

    private ZukoSeekingHonor(final ZukoSeekingHonor card) {
        super(card);
    }

    @Override
    public ZukoSeekingHonor copy() {
        return new ZukoSeekingHonor(this);
    }
}
