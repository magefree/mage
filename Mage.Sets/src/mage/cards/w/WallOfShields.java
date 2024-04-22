

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class WallOfShields extends CardImpl {

    public WallOfShields (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Banding
        this.addAbility(BandingAbility.getInstance());
    }

    private WallOfShields(final WallOfShields card) {
        super(card);
    }

    @Override
    public WallOfShields copy() {
        return new WallOfShields(this);
    }

}
