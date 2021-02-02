
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class WhirlwindAdept extends CardImpl {

    public WhirlwindAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private WhirlwindAdept(final WhirlwindAdept card) {
        super(card);
    }

    @Override
    public WhirlwindAdept copy() {
        return new WhirlwindAdept(this);
    }
}
