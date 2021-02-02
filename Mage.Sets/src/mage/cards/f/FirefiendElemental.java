
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class FirefiendElemental extends CardImpl {

    public FirefiendElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Renown 1
        this.addAbility(new RenownAbility(1));
    }

    private FirefiendElemental(final FirefiendElemental card) {
        super(card);
    }

    @Override
    public FirefiendElemental copy() {
        return new FirefiendElemental(this);
    }
}
