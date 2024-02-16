
package mage.cards.s;

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
public final class ShamblingStrider extends CardImpl {

    public ShamblingStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.YETI);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {R}{G}: Shambling Strider gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, -1, Duration.EndOfTurn), new ManaCostsImpl<>("{R}{G}")));
    }

    private ShamblingStrider(final ShamblingStrider card) {
        super(card);
    }

    @Override
    public ShamblingStrider copy() {
        return new ShamblingStrider(this);
    }
}
