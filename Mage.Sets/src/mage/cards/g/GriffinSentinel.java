

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class GriffinSentinel extends CardImpl {

    public GriffinSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.GRIFFIN);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
    }

    private GriffinSentinel(final GriffinSentinel card) {
        super(card);
    }

    @Override
    public GriffinSentinel copy() {
        return new GriffinSentinel(this);
    }

}
