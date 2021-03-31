package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CulminationOfStudies extends CardImpl {

    public CulminationOfStudies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{R}");

        // Exile the top X cards of your library. For each land card exiled this way, create a Treasure token. For each blue card exiled this way, draw a card. For each red card exiled this way, Culmination of Studies deals 1 damage to each opponent.
        this.getSpellAbility().addEffect(new CulminationOfStudiesEffect());
    }

    private CulminationOfStudies(final CulminationOfStudies card) {
        super(card);
    }

    @Override
    public CulminationOfStudies copy() {
        return new CulminationOfStudies(this);
    }
}

class CulminationOfStudiesEffect extends OneShotEffect {

    private static final FilterCard filterBlue = new FilterCard();
    private static final FilterCard filterRed = new FilterCard();

    static {
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filterRed.add(new ColorPredicate(ObjectColor.RED));
    }

    CulminationOfStudiesEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top X cards of your library. For each land card exiled this way, " +
                "create a Treasure token. For each blue card exiled this way, draw a card. " +
                "For each red card exiled this way, {this} deals 1 damage to each opponent";
    }

    private CulminationOfStudiesEffect(final CulminationOfStudiesEffect effect) {
        super(effect);
    }

    @Override
    public CulminationOfStudiesEffect copy() {
        return new CulminationOfStudiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, source.getManaCostsToPay().getX()));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.EXILED);
        int landCards = cards.count(StaticFilters.FILTER_CARD_LAND, game);
        int blueCards = cards.count(filterBlue, game);
        int redCards = cards.count(filterRed, game);
        if (landCards > 0) {
            new TreasureToken().putOntoBattlefield(landCards, game, source, source.getControllerId());
        }
        if (blueCards > 0) {
            player.drawCards(blueCards, source, game);
        }
        if (redCards > 0) {
            new DamagePlayersEffect(redCards, TargetController.OPPONENT).apply(game, source);
        }
        return true;
    }
}
