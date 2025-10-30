package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeonardoTheBalance extends CardImpl {

    public LeonardoTheBalance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a token you control enters, you may put a +1/+1 counter on each creature you control. Do this only once each turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
                ), StaticFilters.FILTER_PERMANENT_TOKEN
        ).setDoOnlyOnceEachTurn(true));

        // {W}{U}{B}{R}{G}: Creatures you control gain menace, trample, and lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityAllEffect(
                        new MenaceAbility(false), Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("creatures you control gain menace"),
                new ManaCostsImpl<>("{W}{U}{B}{R}{G}")
        );
        ability.addEffect(new GainAbilityAllEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText(", trample"));
        ability.addEffect(new GainAbilityAllEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText(", and lifelink until end of turn"));
        this.addAbility(ability);

        // Partner--Character select
        this.addAbility(PartnerVariantType.CHARACTER_SELECT.makeAbility());
    }

    private LeonardoTheBalance(final LeonardoTheBalance card) {
        super(card);
    }

    @Override
    public LeonardoTheBalance copy() {
        return new LeonardoTheBalance(this);
    }
}
