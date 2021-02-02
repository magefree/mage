
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class LeafDancer extends CardImpl {

    public LeafDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.CENTAUR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());
    }

    private LeafDancer(final LeafDancer card) {
        super(card);
    }

    @Override
    public LeafDancer copy() {
        return new LeafDancer(this);
    }
}
