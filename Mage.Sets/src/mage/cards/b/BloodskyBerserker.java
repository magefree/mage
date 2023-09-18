package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodskyBerserker extends CardImpl {

    public BloodskyBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast your second spell each turn, put two +1/+1 counters on Bloodsky Berserker.
        Ability ability = new CastSecondSpellTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2))
        );
        // It gains menace until end of turn.
        ability.addEffect(new GainAbilitySourceEffect(
                new MenaceAbility(), Duration.EndOfTurn
        ).setText("It gains menace until end of turn. " +
                "<i>(It can't be blocked except by two or more creatures.)</i>"));
        this.addAbility(ability);
    }

    private BloodskyBerserker(final BloodskyBerserker card) {
        super(card);
    }

    @Override
    public BloodskyBerserker copy() {
        return new BloodskyBerserker(this);
    }
}
