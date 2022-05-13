package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class SultaiAscendancy extends CardImpl {

    public SultaiAscendancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{G}{U}");

        // At the beginning of your upkeep, look at the top two cards of your library.
        // Put any number of them into your graveyard and the rest back on top of your library in any order.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new LookLibraryAndPickControllerEffect(2, Integer.MAX_VALUE, PutCards.GRAVEYARD, PutCards.TOP_ANY),
                TargetController.YOU, false));
    }

    private SultaiAscendancy(final SultaiAscendancy card) {
        super(card);
    }

    @Override
    public SultaiAscendancy copy() {
        return new SultaiAscendancy(this);
    }
}
