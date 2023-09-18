
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class LavafumeInvoker extends CardImpl {

    public LavafumeInvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(3, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{8}")));
    }

    private LavafumeInvoker(final LavafumeInvoker card) {
        super(card);
    }

    @Override
    public LavafumeInvoker copy() {
        return new LavafumeInvoker(this);
    }
}
