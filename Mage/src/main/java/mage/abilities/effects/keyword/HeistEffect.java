package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CastManaAdjustment;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

import java.util.List;
import java.util.stream.Collectors;

public class HeistEffect extends OneShotEffect {

    public HeistEffect() {
        super(Outcome.Benefit);
        staticText = "heist target opponent's library";
    }

    private HeistEffect(final HeistEffect effect) {
        super(effect);
    }

    @Override
    public HeistEffect copy() {
        return new HeistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null || opponent == null) {
            return false;
        }
        List<Card> candidates = opponent.getLibrary().getCards(game).stream()
                .filter(card -> !card.isLand(game)).collect(Collectors.toList());
        Cards offered = new CardsImpl();
        while (!candidates.isEmpty() && offered.size() < 3) {
            Card card = RandomUtil.randomFromCollection(candidates);
            offered.add(card);
            candidates.remove(card);
        }
        if (offered.isEmpty()) {
            return true;
        }
        player.lookAtCards("Heist", offered, game);
        TargetCard target = new TargetCard(Zone.LIBRARY, StaticFilters.FILTER_CARD).withNotTarget(true);
        if (!player.choose(outcome, offered, target, source, game)) {
            return false;
        }
        return new ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect(true, CastManaAdjustment.AS_THOUGH_ANY_MANA_TYPE)
                .setTargetPointer(new FixedTarget(target.getFirstTarget(), game)).apply(game, source);
    }
}
