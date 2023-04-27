
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author nantuko
 */
public final class FeralRidgewolf extends CardImpl {

    public FeralRidgewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(TrampleAbility.getInstance());

        // {1}{R}: Feral Ridgewolf gets +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")));
    }

    private FeralRidgewolf(final FeralRidgewolf card) {
        super(card);
    }

    @Override
    public FeralRidgewolf copy() {
        return new FeralRidgewolf(this);
    }
}
