
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class ShadeOfTrokair extends CardImpl {

    public ShadeOfTrokair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.SHADE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {W}: Shade of Trokair gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.W)));
        
        // Suspend 3-{W}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{W}"), this));
    }

    private ShadeOfTrokair(final ShadeOfTrokair card) {
        super(card);
    }

    @Override
    public ShadeOfTrokair copy() {
        return new ShadeOfTrokair(this);
    }
}
