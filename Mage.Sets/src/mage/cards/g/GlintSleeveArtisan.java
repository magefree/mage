
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FabricateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author emerald000
 */
public final class GlintSleeveArtisan extends CardImpl {

    public GlintSleeveArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Fabricate 1
        this.addAbility(new FabricateAbility(1));
    }

    private GlintSleeveArtisan(final GlintSleeveArtisan card) {
        super(card);
    }

    @Override
    public GlintSleeveArtisan copy() {
        return new GlintSleeveArtisan(this);
    }
}
