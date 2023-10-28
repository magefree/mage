package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HitTheMotherLode extends CardImpl {

    public HitTheMotherLode(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}{R}");

        // Discover 10. If the discovered card's mana value is less than 10, create a number of tapped Treasure tokens equal to the difference.
        this.getSpellAbility().addEffect(new HitTheMotherLodeEffect());
    }

    private HitTheMotherLode(final HitTheMotherLode card) {
        super(card);
    }

    @Override
    public HitTheMotherLode copy() {
        return new HitTheMotherLode(this);
    }
}

class HitTheMotherLodeEffect extends OneShotEffect {

    HitTheMotherLodeEffect() {
        super(Outcome.Benefit);
        staticText = "Discover 10. If the discovered card's mana value is less than 10, "
                + "create a number of tapped Treasure tokens equal to the difference.";
    }

    private HitTheMotherLodeEffect(final HitTheMotherLodeEffect effect) {
        super(effect);
    }

    @Override
    public HitTheMotherLodeEffect copy() {
        return new HitTheMotherLodeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Card card = DiscoverEffect.doDiscover(controller, 10, game, source);
        int value = card == null ? 0 : card.getManaValue();
        if (value >= 10) {
            return false;
        }

        return new CreateTokenEffect(new TreasureToken(), 10 - value, true)
                .apply(game, source);
    }

}