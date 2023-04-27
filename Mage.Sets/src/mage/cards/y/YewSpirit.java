
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class YewSpirit extends CardImpl {

    public YewSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{G}{G}: Yew Spirit gets +X/+X until end of turn, where X is its power.
        SourcePermanentPowerCount x = new SourcePermanentPowerCount();
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(x, x, Duration.EndOfTurn, true),
                new ManaCostsImpl<>("{2}{G}{G}")));
    }

    private YewSpirit(final YewSpirit card) {
        super(card);
    }

    @Override
    public YewSpirit copy() {
        return new YewSpirit(this);
    }
}
