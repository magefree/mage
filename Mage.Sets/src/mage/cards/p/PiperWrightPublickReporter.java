package mage.cards.p;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author Cguy7777
 */
public final class PiperWrightPublickReporter extends CardImpl {

    public PiperWrightPublickReporter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Piper Wright deals combat damage to a player, investigate that many times.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new InvestigateEffect(SavedDamageValue.MANY)
                        .setText("investigate that many times. <i>(To investigate, create a Clue token. " +
                                "It's an artifact with \"{2}, Sacrifice this artifact: Draw a card.\")</i>"),
                false));

        // Whenever you sacrifice a Clue, put a +1/+1 counter on target creature you control.
        Ability ability = new SacrificePermanentTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), StaticFilters.FILTER_CONTROLLED_CLUE);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private PiperWrightPublickReporter(final PiperWrightPublickReporter card) {
        super(card);
    }

    @Override
    public PiperWrightPublickReporter copy() {
        return new PiperWrightPublickReporter(this);
    }
}
