
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
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
 * @author hanasu
 */
public final class FolkOfThePines extends CardImpl {

    public FolkOfThePines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // {1}{G}: Folk of the Pines gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{G}")));
    }

    private FolkOfThePines(final FolkOfThePines card) {
        super(card);
    }

    @Override
    public FolkOfThePines copy() {
        return new FolkOfThePines(this);
    }
}
