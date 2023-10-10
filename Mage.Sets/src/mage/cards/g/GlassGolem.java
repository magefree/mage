

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class GlassGolem extends CardImpl {

    public GlassGolem (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(2);
    }

    private GlassGolem(final GlassGolem card) {
        super(card);
    }

    @Override
    public GlassGolem copy() {
        return new GlassGolem(this);
    }

}
