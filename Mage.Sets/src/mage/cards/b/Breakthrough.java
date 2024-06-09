package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class Breakthrough extends CardImpl {

    public Breakthrough(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}");

        // Draw four cards,
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));

        //then choose X cards in your hand and discard the rest.
        this.getSpellAbility().addEffect(new BreakthroughEffect());
    }

    private Breakthrough(final Breakthrough card) {
        super(card);
    }

    @Override
    public Breakthrough copy() {
        return new Breakthrough(this);
    }
}

class BreakthroughEffect extends OneShotEffect {

    BreakthroughEffect() {
        super(Outcome.Discard);
        this.staticText = ", then choose X cards in your hand and discard the rest.";
    }

    private BreakthroughEffect(final BreakthroughEffect effect) {
        super(effect);
    }

    @Override
    public BreakthroughEffect copy() {
        return new BreakthroughEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int amountToKeep = source.getManaCostsToPay().getX();
        if (amountToKeep == 0) {
            player.discard(player.getHand(), false, source, game);
        } else if (amountToKeep < player.getHand().size()) {
            TargetCardInHand target = new TargetCardInHand(amountToKeep, StaticFilters.FILTER_CARD);
            target.setTargetName("cards to keep");
            target.choose(Outcome.Benefit, player.getId(), source.getSourceId(), source, game);
            Cards cards = player.getHand().copy();
            cards.removeIf(target.getTargets()::contains);
            player.discard(cards, false, source, game);
        }
        return true;
    }
}
