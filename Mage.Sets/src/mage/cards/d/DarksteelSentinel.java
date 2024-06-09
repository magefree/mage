

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class DarksteelSentinel extends CardImpl {

    public DarksteelSentinel (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(FlashAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(IndestructibleAbility.getInstance());
    }

    private DarksteelSentinel(final DarksteelSentinel card) {
        super(card);
    }

    @Override
    public DarksteelSentinel copy() {
        return new DarksteelSentinel(this);
    }

}
