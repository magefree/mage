
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class CovertOperative extends CardImpl {

    public CovertOperative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Covert Operative can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private CovertOperative(final CovertOperative card) {
        super(card);
    }

    @Override
    public CovertOperative copy() {
        return new CovertOperative(this);
    }
}
