
package mage.cards.w;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterLandCard;
import mage.game.permanent.token.BlackGreenWormToken;

/**
 *
 * @author LevelX2
 */
public final class WormHarvest extends CardImpl {

    public WormHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B/G}{B/G}{B/G}");

        // Create a 1/1 black and green Worm creature token for each land card in your graveyard.
        CardsInControllerGraveyardCount value = new CardsInControllerGraveyardCount(new FilterLandCard());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BlackGreenWormToken(), value));

        // Retrace
        this.addAbility(new RetraceAbility(this));
    }

    private WormHarvest(final WormHarvest card) {
        super(card);
    }

    @Override
    public WormHarvest copy() {
        return new WormHarvest(this);
    }
}
