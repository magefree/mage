package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 * @author sandbo00
 */
public final class MarcusMutantMayor extends CardImpl {

    public MarcusMutantMayor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{3}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);

        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a creature you control deals combat damage to a player, draw a card if that creature has a +1/+1 counter on it. If it doesn’t, put a +1/+1 counter on it.
        Ability ability = new DealsDamageToAPlayerAllTriggeredAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new SourceHasCounterCondition(CounterType.P1P1),
                "draw a card if that creature has a +1/+1 counter on it. " +
                        "If it doesn't, put a +1/+1 counter on it."),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, false, SetTargetPointer.PERMANENT, true);
        this.addAbility(ability);
    }

    private MarcusMutantMayor(final MarcusMutantMayor card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new MarcusMutantMayor(this);
    }

}
