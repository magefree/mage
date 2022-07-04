package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class VeiledShade extends CardImpl {

    public VeiledShade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SHADE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{B}: Veiled Shade gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{B}")
        ));
    }

    private VeiledShade(final VeiledShade card) {
        super(card);
    }

    @Override
    public VeiledShade copy() {
        return new VeiledShade(this);
    }
}
