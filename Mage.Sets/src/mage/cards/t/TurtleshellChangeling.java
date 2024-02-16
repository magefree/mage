
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessSourceEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Styxo
 */
public final class TurtleshellChangeling extends CardImpl {

    public TurtleshellChangeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        //Changeling
        this.addAbility(new ChangelingAbility());

        //{1}{U}: Switch {this}'s power and toughness until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SwitchPowerToughnessSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U}")));
    }

    private TurtleshellChangeling(final TurtleshellChangeling card) {
        super(card);
    }

    @Override
    public TurtleshellChangeling copy() {
        return new TurtleshellChangeling(this);
    }
}
