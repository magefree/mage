package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactOrEnchantmentCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrilliantRestoration extends CardImpl {

    public BrilliantRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}{W}{W}");

        // Return all artifact and enchantment cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new BrilliantRestorationEffect());
    }

    private BrilliantRestoration(final BrilliantRestoration card) {
        super(card);
    }

    @Override
    public BrilliantRestoration copy() {
        return new BrilliantRestoration(this);
    }
}

class BrilliantRestorationEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterArtifactOrEnchantmentCard();

    BrilliantRestorationEffect() {
        super(Outcome.Benefit);
        staticText = "return all artifact and enchantment cards from your graveyard to the battlefield";
    }

    private BrilliantRestorationEffect(final BrilliantRestorationEffect effect) {
        super(effect);
    }

    @Override
    public BrilliantRestorationEffect copy() {
        return new BrilliantRestorationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        return controller.moveCards(controller.getGraveyard().getCards(filter, game), Zone.BATTLEFIELD, source, game);
    }
}
