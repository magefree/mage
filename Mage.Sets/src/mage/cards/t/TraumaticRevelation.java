package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TraumaticRevelation extends CardImpl {

    public TraumaticRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent reveals their hand. You may choose a creature or battle card from it. If you do, that player discards that card. If you don't, incubate 3.
        this.getSpellAbility().addEffect(new TraumaticRevelationEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private TraumaticRevelation(final TraumaticRevelation card) {
        super(card);
    }

    @Override
    public TraumaticRevelation copy() {
        return new TraumaticRevelation(this);
    }
}

class TraumaticRevelationEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature or battle card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
    }

    TraumaticRevelationEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent reveals their hand. You may choose a creature or battle card from it. " +
                "If you do, that player discards that card. If you don't, incubate 3";
    }

    private TraumaticRevelationEffect(final TraumaticRevelationEffect effect) {
        super(effect);
    }

    @Override
    public TraumaticRevelationEffect copy() {
        return new TraumaticRevelationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        opponent.revealCards(source, opponent.getHand(), game);
        TargetCard target = new TargetCardInHand(0, 1, filter);
        controller.choose(Outcome.Discard, opponent.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            opponent.discard(card, false, source, game);
            return true;
        }
        IncubateEffect.doIncubate(3, game, source);
        return true;
    }
}
