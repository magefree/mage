
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.ExileTopCreatureCardOfGraveyardCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class BarrowGhoul extends CardImpl {

    public BarrowGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, sacrifice Barrow Ghoul unless you exile the top creature card of your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ExileTopCreatureCardOfGraveyardCost(1)), TargetController.YOU, false));
    }

    private BarrowGhoul(final BarrowGhoul card) {
        super(card);
    }

    @Override
    public BarrowGhoul copy() {
        return new BarrowGhoul(this);
    }
}
