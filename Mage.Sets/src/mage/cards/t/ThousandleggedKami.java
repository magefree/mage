

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class ThousandleggedKami extends CardImpl {

    public ThousandleggedKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.addAbility(new SoulshiftAbility(7));
    }

    public ThousandleggedKami(final ThousandleggedKami card) {
        super(card);
    }

    @Override
    public ThousandleggedKami copy() {
        return new ThousandleggedKami(this);
    }

}
