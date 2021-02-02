

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class KamiOfLunacy extends CardImpl {

    public KamiOfLunacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SoulshiftAbility(5));
    }

    private KamiOfLunacy(final KamiOfLunacy card) {
        super(card);
    }

    @Override
    public KamiOfLunacy copy() {
        return new KamiOfLunacy(this);
    }

}
