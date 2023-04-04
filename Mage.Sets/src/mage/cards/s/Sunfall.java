package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Sunfall extends CardImpl {

    public Sunfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Exile all creatures. Incubate X, where X is the number of creatures exiled this way.
        this.getSpellAbility().addEffect(new SunfallEffect());
    }

    private Sunfall(final Sunfall card) {
        super(card);
    }

    @Override
    public Sunfall copy() {
        return new Sunfall(this);
    }
}

class SunfallEffect extends OneShotEffect {

    SunfallEffect() {
        super(Outcome.Benefit);
        staticText = "exile all creatures. Incubate X, where X is the number of creatures exiled this way";
    }

    private SunfallEffect(final SunfallEffect effect) {
        super(effect);
    }

    @Override
    public SunfallEffect copy() {
        return new SunfallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game
        ));
        player.moveCards(cards, Zone.EXILED, source, game);
        new IncubateEffect(cards.size()).apply(game, source);
        return true;
    }
}
