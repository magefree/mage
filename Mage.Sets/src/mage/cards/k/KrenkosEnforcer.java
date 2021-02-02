
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Quercitron
 */
public final class KrenkosEnforcer extends CardImpl {

    public KrenkosEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Intimidate
        this.addAbility(IntimidateAbility.getInstance());
    }

    private KrenkosEnforcer(final KrenkosEnforcer card) {
        super(card);
    }

    @Override
    public KrenkosEnforcer copy() {
        return new KrenkosEnforcer(this);
    }
}
