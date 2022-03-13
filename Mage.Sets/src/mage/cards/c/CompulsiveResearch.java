package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
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
 * @author Plopman
 */
public final class CompulsiveResearch extends CardImpl {

    public CompulsiveResearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Target player draws three cards. Then that player discards two cards unless they discard a land card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(3));
        this.getSpellAbility().addEffect(new CompulsiveResearchDiscardEffect());
    }

    private CompulsiveResearch(final CompulsiveResearch card) {
        super(card);
    }

    @Override
    public CompulsiveResearch copy() {
        return new CompulsiveResearch(this);
    }
}

class CompulsiveResearchDiscardEffect extends OneShotEffect {

    CompulsiveResearchDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "Then that player discards two cards unless they discard a land card";
    }

    private CompulsiveResearchDiscardEffect(final CompulsiveResearchDiscardEffect effect) {
        super(effect);
    }

    @Override
    public CompulsiveResearchDiscardEffect copy() {
        return new CompulsiveResearchDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetPlayer == null || targetPlayer.getHand().isEmpty()) {
            return false;
        }
        if (targetPlayer.getHand().count(StaticFilters.FILTER_CARD_LAND, game) < 1
                || !targetPlayer.chooseUse(outcome, "Discard a land card?", source, game)) {
            return !targetPlayer.discard(2, false, false, source, game).isEmpty();
        }
        TargetDiscard target = new TargetDiscard(StaticFilters.FILTER_CARD_LAND_A, targetPlayer.getId());
        targetPlayer.choose(Outcome.Discard, target, source, game);
        Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
        if (card != null && targetPlayer.discard(card, false, source, game)) {
            return true;
        }
        return !targetPlayer.discard(2, false, false, source, game).isEmpty();
    }
}
