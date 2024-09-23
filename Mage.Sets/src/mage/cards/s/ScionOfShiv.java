package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.BoostSourcePerpetuallyEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author karapuzz14
 */
public final class ScionOfShiv extends CardImpl {

    public ScionOfShiv(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{R}: Scion of Shiv perpetually gets +1/+0.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourcePerpetuallyEffect(1,0), new ManaCostsImpl<>("{2}{R}")));
    }

    private ScionOfShiv(final ScionOfShiv card) {
        super(card);
    }

    @Override
    public ScionOfShiv copy() {
        return new ScionOfShiv(this);
    }
}
