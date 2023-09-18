package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
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
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author TheElk801
 */
public final class BondOfInsight extends CardImpl {

    public BondOfInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Each player puts the top four cards of their library into their graveyard. Return up to two instant and/or sorcery cards from your graveyard to your hand. Exile Bond of Insight.
        this.getSpellAbility().addEffect(new BondOfInsightEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private BondOfInsight(final BondOfInsight card) {
        super(card);
    }

    @Override
    public BondOfInsight copy() {
        return new BondOfInsight(this);
    }
}

class BondOfInsightEffect extends OneShotEffect {

    BondOfInsightEffect() {
        super(Outcome.Benefit);
        staticText = "Each player mills four cards. "
                + "Return up to two instant and/or sorcery cards from your graveyard to your hand.";
    }

    private BondOfInsightEffect(final BondOfInsightEffect effect) {
        super(effect);
    }

    @Override
    public BondOfInsightEffect copy() {
        return new BondOfInsightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            player.millCards(4, source, game);
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(
                0, 2, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, true
        );
        if (!player.choose(outcome, target, source, game)) {
            return false;
        }
        Cards cards = new CardsImpl(target.getTargets());
        return player.moveCards(cards, Zone.HAND, source, game);
    }
}
