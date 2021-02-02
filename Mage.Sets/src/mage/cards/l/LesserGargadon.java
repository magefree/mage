
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author Plopman
 */
public final class LesserGargadon extends CardImpl {

    public LesserGargadon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Whenever Lesser Gargadon attacks or blocks, sacrifice a land.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new SacrificeControllerEffect(new FilterLandPermanent(), 1, ""), false));
    }

    private LesserGargadon(final LesserGargadon card) {
        super(card);
    }

    @Override
    public LesserGargadon copy() {
        return new LesserGargadon(this);
    }
}
