
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FabricateAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class HighspireArtisan extends CardImpl {

    public HighspireArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // Fabricate 1
        this.addAbility(new FabricateAbility(1));
    }

    private HighspireArtisan(final HighspireArtisan card) {
        super(card);
    }

    @Override
    public HighspireArtisan copy() {
        return new HighspireArtisan(this);
    }
}
