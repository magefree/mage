package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SurrakarSpellblade extends CardImpl {

    public SurrakarSpellblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.SURRAKAR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast an instant or sorcery spell, you may put a charge counter on Surrakar Spellblade.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, true
        ));

        // Whenever Surrakar Spellblade deals combat damage to a player, you may draw X cards, where X is the number of charge counters on it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(
                new CountersSourceCount(CounterType.CHARGE)
        ).setText("draw X cards, where X is the number of charge counters on it"), true));
    }

    private SurrakarSpellblade(final SurrakarSpellblade card) {
        super(card);
    }

    @Override
    public SurrakarSpellblade copy() {
        return new SurrakarSpellblade(this);
    }
}
