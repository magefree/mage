package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MindRoots extends CardImpl {

    public MindRoots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{G}");

        // Target player discards two cards. Put up to one land card discarded this way onto the battlefield tapped under your control.
        this.getSpellAbility().addEffect(new MindRootsEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private MindRoots(final MindRoots card) {
        super(card);
    }

    @Override
    public MindRoots copy() {
        return new MindRoots(this);
    }
}

class MindRootsEffect extends OneShotEffect {

    MindRootsEffect() {
        super(Outcome.Benefit);
        staticText = "target player discards two cards. Put up to one land card " +
                "discarded this way onto the battlefield tapped under your control";
    }

    private MindRootsEffect(final MindRootsEffect effect) {
        super(effect);
    }

    @Override
    public MindRootsEffect copy() {
        return new MindRootsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        Cards cards = player.discard(2, 2, false, source, game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return !cards.isEmpty();
        }
        TargetCard target = new TargetCard(0, 1, Zone.ALL, StaticFilters.FILTER_CARD_LAND);
        target.withNotTarget(true);
        controller.choose(Outcome.PutLandInPlay, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return !cards.isEmpty();
        }
        controller.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, false, null
        );
        return true;
    }
}
