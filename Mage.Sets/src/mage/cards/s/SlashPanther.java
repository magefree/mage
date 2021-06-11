
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SlashPanther extends CardImpl {

    public SlashPanther(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}{R/P}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        this.addAbility(HasteAbility.getInstance());
    }

    private SlashPanther(final SlashPanther card) {
        super(card);
    }

    @Override
    public SlashPanther copy() {
        return new SlashPanther(this);
    }
}
