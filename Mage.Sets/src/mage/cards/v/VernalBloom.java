
package mage.cards.v;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author Plopman
 */
public final class VernalBloom extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a Forest");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }
    public VernalBloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}");


        // Whenever a Forest is tapped for mana, its controller adds {G}.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                new AddManaToManaPoolTargetControllerEffect(new Mana(ColoredManaSymbol.G), "their"),
                filter, SetTargetPointer.PLAYER));
    }

    private VernalBloom(final VernalBloom card) {
        super(card);
    }

    @Override
    public VernalBloom copy() {
        return new VernalBloom(this);
    }
}
