
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author LevelX2
 */
public final class SultaiAscendancy extends CardImpl {

    public SultaiAscendancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{G}{U}");

        // At the beginning of your upkeep, look at the top two cards of your library. Put any number of them into your graveyard and the rest on top of your library in any order.
        Effect effect = new LookLibraryAndPickControllerEffect(
                StaticValue.get(2), false, StaticValue.get(2), new FilterCard(), Zone.LIBRARY, true, false, true, Zone.GRAVEYARD, false);
        effect.setText("look at the top two cards of your library. Put any number of them into your graveyard and the rest on top of your library in any order");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false));
    }

    private SultaiAscendancy(final SultaiAscendancy card) {
        super(card);
    }

    @Override
    public SultaiAscendancy copy() {
        return new SultaiAscendancy(this);
    }
}
