
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class HauntedGuardian extends CardImpl {

    public HauntedGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private HauntedGuardian(final HauntedGuardian card) {
        super(card);
    }

    @Override
    public HauntedGuardian copy() {
        return new HauntedGuardian(this);
    }
}
