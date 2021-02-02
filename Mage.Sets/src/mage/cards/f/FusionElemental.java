

package mage.cards.f;

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
public final class FusionElemental extends CardImpl {

    public FusionElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{U}{B}{R}{G}");
        this.subtype.add(SubType.ELEMENTAL);


        this.power = new MageInt(8);
    this.toughness = new MageInt(8);
    }

    private FusionElemental(final FusionElemental card) {
        super(card);
    }

    @Override
    public FusionElemental copy() {
        return new FusionElemental(this);
    }

}
