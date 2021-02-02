

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class KelinoreBat extends CardImpl {

    public KelinoreBat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.BAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    private KelinoreBat(final KelinoreBat card) {
        super(card);
    }

    @Override
    public KelinoreBat copy() {
        return new KelinoreBat(this);
    }

}
