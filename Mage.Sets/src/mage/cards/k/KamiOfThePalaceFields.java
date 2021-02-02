

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class KamiOfThePalaceFields extends CardImpl {

    public KamiOfThePalaceFields(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(new SoulshiftAbility(5));
    }

    private KamiOfThePalaceFields(final KamiOfThePalaceFields card) {
        super(card);
    }

    @Override
    public KamiOfThePalaceFields copy() {
        return new KamiOfThePalaceFields(this);
    }

}
