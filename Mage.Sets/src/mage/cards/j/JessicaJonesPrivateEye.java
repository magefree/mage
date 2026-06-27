package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PutCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class JessicaJonesPrivateEye extends CardImpl {

    public JessicaJonesPrivateEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.subtype.add(SubType.HERO);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}, Put a stun counter on Jessica Jones: Exile the top X cards of your library, where X is Jessica Jones's power. You may play those cards this turn.
        Ability ability = new SimpleActivatedAbility(
            new ExileTopXMayPlayUntilEffect(SourcePermanentPowerValue.NOT_NEGATIVE, false, Duration.EndOfTurn),
            new TapSourceCost()
        );
        ability.addCost(new PutCountersSourceCost(CounterType.STUN.createInstance()));
        this.addAbility(ability);
    }

    private JessicaJonesPrivateEye(final JessicaJonesPrivateEye card) {
        super(card);
    }

    @Override
    public JessicaJonesPrivateEye copy() {
        return new JessicaJonesPrivateEye(this);
    }
}
