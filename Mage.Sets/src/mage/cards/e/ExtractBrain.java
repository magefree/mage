package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExtractBrain extends CardImpl {

    public ExtractBrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{B}");

        // Target opponent chooses X cards from their hand. Look at those cards. You may cast a spell from among them without paying its mana cost.
        this.getSpellAbility().addEffect(new ExtractBrainEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private ExtractBrain(final ExtractBrain card) {
        super(card);
    }

    @Override
    public ExtractBrain copy() {
        return new ExtractBrain(this);
    }
}

class ExtractBrainEffect extends OneShotEffect {

    ExtractBrainEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent chooses X cards from their hand. Look at those cards. " +
                "You may cast a spell from among them without paying its mana cost";
    }

    private ExtractBrainEffect(final ExtractBrainEffect effect) {
        super(effect);
    }

    @Override
    public ExtractBrainEffect copy() {
        return new ExtractBrainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        int xValue = source.getManaCostsToPay().getX();
        if (controller == null || opponent == null || opponent.getHand().isEmpty() || xValue < 1) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(
                Math.min(opponent.getHand().size(), xValue), StaticFilters.FILTER_CARD
        );
        opponent.choose(Outcome.Detriment, opponent.getHand(), target, game);
        Cards cards = new CardsImpl(target.getTargets());
        controller.lookAtCards(source, null, cards, game);
        CardUtil.castSpellWithAttributesForFree(controller, source, game, cards, StaticFilters.FILTER_CARD);
        return true;
    }
}
