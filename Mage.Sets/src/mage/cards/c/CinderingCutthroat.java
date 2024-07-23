package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.OpponentsLostLifeHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CinderingCutthroat extends CardImpl {

    public CinderingCutthroat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Cindering Cutthroat enters with a +1/+1 counter on it if an opponent lost life this turn.
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        OpponentsLostLifeCondition.instance, ""
                ),
                "with a +1/+1 counter on it if an opponent lost life this turn"
        ).addHint(OpponentsLostLifeHint.instance));

        // {1}{B/R}: Cindering Cutthroat gains menace until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilitySourceEffect(new MenaceAbility(false), Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{B/R}")
        ));
    }

    private CinderingCutthroat(final CinderingCutthroat card) {
        super(card);
    }

    @Override
    public CinderingCutthroat copy() {
        return new CinderingCutthroat(this);
    }
}
