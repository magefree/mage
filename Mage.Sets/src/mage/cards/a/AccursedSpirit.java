
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class AccursedSpirit extends CardImpl {

    public AccursedSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Intimidate
        this.addAbility(IntimidateAbility.getInstance());
    }

    private AccursedSpirit(final AccursedSpirit card) {
        super(card);
    }

    @Override
    public AccursedSpirit copy() {
        return new AccursedSpirit(this);
    }
}
