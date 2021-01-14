package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class InfernalPet extends CardImpl {

    public InfernalPet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.IMP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast your second spell each turn, put a +1/+1 counter on Infernal Pet and it gains flying until end of turn.
        Ability ability = new CastSecondSpellTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        );
        ability.addEffect(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("and it gains flying until end of turn"));
        this.addAbility(ability);
    }

    private InfernalPet(final InfernalPet card) {
        super(card);
    }

    @Override
    public InfernalPet copy() {
        return new InfernalPet(this);
    }
}
