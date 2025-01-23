package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RocketeerBoostbuggy extends CardImpl {

    public RocketeerBoostbuggy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}{G}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever this Vehicle attacks, create a Treasure token.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Exhaust -- {3}: This Vehicle becomes an artifact creature. Put a +1/+1 counter on it.
        Ability ability = new ExhaustAbility(new AddCardTypeSourceEffect(
                Duration.Custom, CardType.ARTIFACT, CardType.CREATURE
        ), new GenericManaCost(3));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .setText("Put a +1/+1 counter on it"));
        this.addAbility(ability);

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private RocketeerBoostbuggy(final RocketeerBoostbuggy card) {
        super(card);
    }

    @Override
    public RocketeerBoostbuggy copy() {
        return new RocketeerBoostbuggy(this);
    }
}
