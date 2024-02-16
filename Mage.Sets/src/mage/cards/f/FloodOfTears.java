package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FloodOfTears extends CardImpl {

    public FloodOfTears(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Return all nonland permanents to their owners' hands. If you return four or more nontoken permanents you control this way,
        // you may put a permanent card from your hand onto the battlefield.
        this.getSpellAbility().addEffect(new FloodOfTearsEffect());
    }

    private FloodOfTears(final FloodOfTears card) {
        super(card);
    }

    @Override
    public FloodOfTears copy() {
        return new FloodOfTears(this);
    }
}

class FloodOfTearsEffect extends OneShotEffect {

    FloodOfTearsEffect() {
        super(Outcome.Detriment);
        staticText = "Return all nonland permanents to their owners' hands. If you return four or more nontoken permanents you control this way,"
                + " you may put a permanent card from your hand onto the battlefield.";
    }

    private FloodOfTearsEffect(final FloodOfTearsEffect effect) {
        super(effect);
    }

    @Override
    public FloodOfTearsEffect copy() {
        return new FloodOfTearsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Permanent> nonlands = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_NON_LAND, source.getControllerId(), source, game
        );
        Cards cards = new CardsImpl();
        if (!nonlands.isEmpty()) {
            nonlands.forEach(cards::add);
            boolean putIntoPlay = nonlands.stream()
                    .filter(permanent -> permanent.isControlledBy(player.getId()))
                    .filter(permanent -> !(permanent instanceof PermanentToken))
                    .count() > 3;
            player.moveCards(cards, Zone.HAND, source, game);
            if (putIntoPlay) {
                new PutCardFromHandOntoBattlefieldEffect().apply(game, source);
            }
            return true;
        }
        return false;
    }
}