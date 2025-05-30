package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CameraLauncher extends CardImpl {

    public CameraLauncher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Exhaust -- {3}: Put a +1/+1 counter on this creature. Create a 1/1 colorless Thopter artifact creature token with flying.
        Ability ability = new ExhaustAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(3)
        );
        ability.addEffect(new CreateTokenEffect(new ThopterColorlessToken()));
        this.addAbility(ability);
    }

    private CameraLauncher(final CameraLauncher card) {
        super(card);
    }

    @Override
    public CameraLauncher copy() {
        return new CameraLauncher(this);
    }
}
