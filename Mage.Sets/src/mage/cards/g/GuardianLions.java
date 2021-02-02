
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class GuardianLions extends CardImpl {

    public GuardianLions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private GuardianLions(final GuardianLions card) {
        super(card);
    }

    @Override
    public GuardianLions copy() {
        return new GuardianLions(this);
    }
}
