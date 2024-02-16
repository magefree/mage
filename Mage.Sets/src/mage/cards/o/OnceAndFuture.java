package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OnceAndFuture extends CardImpl {

    private static final FilterCard filter = new FilterCard("other card from your graveyard");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public OnceAndFuture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Return target card from your graveyard to your hand. Put up to one other target card from your graveyard on top of your library. Exile Once and Future.
        // Adamant — If at least three green mana was spent to cast this spell, instead return those cards to your hand and exile Once and Future.
        this.getSpellAbility().addEffect(new OnceAndFutureEffect());

        Target target = new TargetCardInYourGraveyard().withChooseHint("to put in your hand");
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        target = new TargetCardInYourGraveyard(0, 1, filter).withChooseHint("to put on top of your library");
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
        if (getCondition().apply(game, source)) {
            Cards cards = new CardsImpl(card1);
            cards.add(card2);
            player.moveCards(cards, Zone.HAND, source, game);
        } else {
            player.moveCards(card1, Zone.HAND, source, game);
            game.getState().processAction(game);
            player.putCardsOnTopOfLibrary(card2, game, source, true);
        }
        game.getState().processAction(game);
        return new ExileSpellEffect().apply(game, source);
    }

    @Override
    public Condition getCondition() {
        return AdamantCondition.GREEN;
    }
}
