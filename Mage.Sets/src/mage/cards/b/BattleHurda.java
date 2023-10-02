

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class BattleHurda extends CardImpl {

    public BattleHurda (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private BattleHurda(final BattleHurda card) {
        super(card);
    }

    @Override
    public BattleHurda copy() {
        return new BattleHurda(this);
    }

}
