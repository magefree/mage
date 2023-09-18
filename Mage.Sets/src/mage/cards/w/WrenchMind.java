package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetDiscard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WrenchMind extends CardImpl {

    public WrenchMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}");

        // Target player discards two cards unless they discard an artifact card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new WrenchMindEffect());
    }

    private WrenchMind(final WrenchMind card) {
        super(card);
    }

    @Override
    public WrenchMind copy() {
        return new WrenchMind(this);
    }
}

class WrenchMindEffect extends OneShotEffect {

    WrenchMindEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player discards two cards unless they discard an artifact card";
    }

    private WrenchMindEffect(final WrenchMindEffect effect) {
        super(effect);
    }

    @Override
    public WrenchMindEffect copy() {
        return new WrenchMindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetPlayer == null || targetPlayer.getHand().isEmpty()) {
            return false;
        }
        if (targetPlayer.getHand().count(StaticFilters.FILTER_CARD_ARTIFACT, game) < 1
                || !targetPlayer.chooseUse(Outcome.Benefit, "Discard an artifact card?", source, game)) {
            return !targetPlayer.discard(2, false, false, source, game).isEmpty();
        }
        TargetDiscard target = new TargetDiscard(StaticFilters.FILTER_CARD_ARTIFACT_AN, targetPlayer.getId());
        targetPlayer.choose(Outcome.Discard, target, source, game);
        Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
        if (card != null && targetPlayer.discard(card, false, source, game)) {
            return true;
        }
        return !targetPlayer.discard(2, false, false, source, game).isEmpty();
    }
}
