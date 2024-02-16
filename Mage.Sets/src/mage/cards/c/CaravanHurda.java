

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class CaravanHurda extends CardImpl {

    public CaravanHurda (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(5);
        this.addAbility(LifelinkAbility.getInstance());
    }

    private CaravanHurda(final CaravanHurda card) {
        super(card);
    }

    @Override
    public CaravanHurda copy() {
        return new CaravanHurda(this);
    }
}
