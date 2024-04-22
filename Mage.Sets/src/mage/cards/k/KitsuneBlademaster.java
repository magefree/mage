

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class KitsuneBlademaster extends CardImpl {

    public KitsuneBlademaster (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(new BushidoAbility(1));
    }

    private KitsuneBlademaster(final KitsuneBlademaster card) {
        super(card);
    }

    @Override
    public KitsuneBlademaster copy() {
        return new KitsuneBlademaster(this);
    }

}
