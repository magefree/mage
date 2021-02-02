
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class SigiledStarfish extends CardImpl {

    public SigiledStarfish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.STARFISH);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {T}: Scry 1.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(1), new TapSourceCost()));
    }

    private SigiledStarfish(final SigiledStarfish card) {
        super(card);
    }

    @Override
    public SigiledStarfish copy() {
        return new SigiledStarfish(this);
    }
}
