package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OnceAndFuture extends CardImpl {

    public OnceAndFuture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Return target card from your graveyard to your hand. Put up to one other target card from your graveyard on top of your library. Exile Once and Future.
        // Adamant — If at least three green mana was spent to cast this spell, instead return those cards to your hand and exile Once and Future.
        this.getSpellAbility().addEffect(new OnceAndFutureEffect());

        Target target = new TargetCardInYourGraveyard().withChooseHint("To put in your hand");
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        target = new TargetCardInYourGraveyard(0, 1).withChooseHint("To put on top of your library");
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);

    }

    private OnceAndFuture(final OnceAndFuture card) {
        super(card);
    }

    @Override
    public OnceAndFuture copy() {
        return new OnceAndFuture(this);
    }
}

class OnceAndFutureEffect extends OneShotEffect {

    OnceAndFutureEffect() {
        super(Outcome.Benefit);
        staticText = "Return target card from your graveyard to your hand. " +
                "Put up to one other target card from your graveyard on top of your library. Exile {this}." +
                "<br><i>Adamant</i> &mdash; If at least three green mana was spent to cast this spell, " +
                "instead return those cards to your hand and exile {this}.";
    }

    private OnceAndFutureEffect(final OnceAndFutureEffect effect) {
        super(effect);
    }

    @Override
    public OnceAndFutureEffect copy() {
        return new OnceAndFutureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card1 = game.getCard(source.getFirstTarget());
        Card card2 = game.getCard(source.getTargets().get(1).getFirstTarget());
        if (card1 == null) {
            card1 = card2;
            card2 = null;
        }
        if (card1 == null) {
            return false;
        }
        if (card2 == null) {
            player.putInHand(card1, game);
            return new ExileSpellEffect().apply(game, source);
        }
        if (AdamantCondition.GREEN.apply(game, source)) {
            Cards cards = new CardsImpl();
            cards.add(card1);
            cards.add(card2);
            player.moveCards(cards, Zone.HAND, source, game);
            return new ExileSpellEffect().apply(game, source);
        }
        player.putInHand(card1, game);
        player.putCardsOnTopOfLibrary(new CardsImpl(card2), game, source, false);
        return new ExileSpellEffect().apply(game, source);
    }
}