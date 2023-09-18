
package mage.cards.y;

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
 * @author LoneFox
 */
public final class YavimayaAncients extends CardImpl {

    public YavimayaAncients(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(7);

        // {G}: Yavimaya Ancients gets +1/-2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, -2, Duration.EndOfTurn), new ManaCostsImpl<>("{G}")));
    }

    private YavimayaAncients(final YavimayaAncients card) {
        super(card);
    }

    @Override
    public YavimayaAncients copy() {
        return new YavimayaAncients(this);
    }
}
