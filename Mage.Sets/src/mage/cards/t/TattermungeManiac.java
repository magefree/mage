
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class TattermungeManiac extends CardImpl {

    public TattermungeManiac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R/G}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private TattermungeManiac(final TattermungeManiac card) {
        super(card);
    }

    @Override
    public TattermungeManiac copy() {
        return new TattermungeManiac(this);
    }
}
