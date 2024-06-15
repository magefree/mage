package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SelectiveObliteration extends CardImpl {

    public SelectiveObliteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{C}{C}");

        // Each player chooses a color. Then exile each permanent unless it's colorless or it's only the color its controller chose.
        this.getSpellAbility().addEffect(new SelectiveObliterationEffect());
    }

    private SelectiveObliteration(final SelectiveObliteration card) {
        super(card);
    }

    @Override
    public SelectiveObliteration copy() {
        return new SelectiveObliteration(this);
    }
}

class SelectiveObliterationEffect extends OneShotEffect {

    private static final class SelectiveObliterationPredicate implements Predicate<Permanent> {

        private final UUID playerId;
        private final ObjectColor color;

        SelectiveObliterationPredicate(UUID playerId, ObjectColor color) {
            this.playerId = playerId;
            this.color = color;
        }

        @Override
        public boolean apply(Permanent input, Game game) {
            return !input.getColor(game).isColorless()
                    && !(input.isControlledBy(playerId) && input.getColor(game).equals(color));
        }
    }

    SelectiveObliterationEffect() {
        super(Outcome.Benefit);
        staticText = "each player chooses a color. Then exile each permanent " +
                "unless it's colorless or it's only the color its controller chose";
    }

    private SelectiveObliterationEffect(final SelectiveObliterationEffect effect) {
        super(effect);
    }

    @Override
    public SelectiveObliterationEffect copy() {
        return new SelectiveObliterationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        FilterPermanent filter = new FilterPermanent();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            ChoiceColor choice = new ChoiceColor(true);
            player.choose(Outcome.Exile, choice, game);
            ObjectColor color = choice.getColor();
            game.informPlayers(player.getLogName() + " chooses " + color.getDescription());
            filter.add(new SelectiveObliterationPredicate(playerId, choice.getColor()));
        }
        List<Permanent> permanents = game
                .getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game);
        return controller.moveCards(new CardsImpl(permanents), Zone.EXILED, source, game);
    }
}

