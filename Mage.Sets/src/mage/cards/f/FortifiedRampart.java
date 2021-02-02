
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class FortifiedRampart extends CardImpl {

    public FortifiedRampart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(6);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
    }

    private FortifiedRampart(final FortifiedRampart card) {
        super(card);
    }

    @Override
    public FortifiedRampart copy() {
        return new FortifiedRampart(this);
    }
}
