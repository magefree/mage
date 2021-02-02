
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class MonasterySwiftspear extends CardImpl {

    public MonasterySwiftspear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private MonasterySwiftspear(final MonasterySwiftspear card) {
        super(card);
    }

    @Override
    public MonasterySwiftspear copy() {
        return new MonasterySwiftspear(this);
    }
}
