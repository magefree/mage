
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class PygmyPyrosaur extends CardImpl {

    public PygmyPyrosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.LIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Pygmy Pyrosaur can't block.
        this.addAbility(new CantBlockAbility());
        // {R}: Pygmy Pyrosaur gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));
    }

    private PygmyPyrosaur(final PygmyPyrosaur card) {
        super(card);
    }

    @Override
    public PygmyPyrosaur copy() {
        return new PygmyPyrosaur(this);
    }
}
