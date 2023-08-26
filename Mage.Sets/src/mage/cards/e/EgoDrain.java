package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Xanderhall
 */
public final class EgoDrain extends CardImpl {

    public EgoDrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");
        
        // Target opponent reveals their hand. You choose a nonland card from it. That player discards that card. 
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND));
        
        // If you don't control a Faerie, exile a card from your hand.
        this.getSpellAbility().addEffect(new EgoDrainEffect());
    }

    private EgoDrain(final EgoDrain card) {
        super(card);
    }

    @Override
    public EgoDrain copy() {
        return new EgoDrain(this);
    }
}

class EgoDrainEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent(SubType.FAERIE, "");

    EgoDrainEffect() {
        super(Outcome.Detriment);
        this.staticText = "if you don't control a Faerie, exile a card from your hand.";
    }

    private EgoDrainEffect(final EgoDrainEffect effect) {
        super(effect);
    }

    @Override
    public EgoDrainEffect copy() {
        return new EgoDrainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.getHand().size() == 0 || game.getBattlefield().countAll(filter, player.getId(), game) >= 1) {
            return true;
        }

        TargetCard target = new TargetCardInHand(new FilterCard("card in your hand (to exile)"));
        return player.choose(Outcome.Detriment, player.getHand(), target, source, game)
            && player.moveCards(game.getCard(target.getFirstTarget()), Zone.EXILED, source, game);
    }
}
