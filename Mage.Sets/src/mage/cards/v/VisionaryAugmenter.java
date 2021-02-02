
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FabricateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class VisionaryAugmenter extends CardImpl {

    public VisionaryAugmenter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Fabricate 2
        this.addAbility(new FabricateAbility(2));
    }

    private VisionaryAugmenter(final VisionaryAugmenter card) {
        super(card);
    }

    @Override
    public VisionaryAugmenter copy() {
        return new VisionaryAugmenter(this);
    }
}
