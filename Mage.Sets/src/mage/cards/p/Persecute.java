package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Persecute extends CardImpl {

    public Persecute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Choose a color. Target player reveals their hand and discards all cards of that color.
        this.getSpellAbility().addEffect(new PersecuteEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Persecute(final Persecute card) {
        super(card);
    }

    @Override
    public Persecute copy() {
        return new Persecute(this);
    }
}

class PersecuteEffect extends OneShotEffect {

    PersecuteEffect() {
        super(Outcome.Discard);
        this.staticText = "Choose a color. Target player reveals their hand and discards all cards of that color";
    }

    private PersecuteEffect(final PersecuteEffect effect) {
        super(effect);
    }

    @Override
    public PersecuteEffect copy() {
        return new PersecuteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        ChoiceColor choice = new ChoiceColor();
        if (controller == null
                || sourceObject == null
                || targetPlayer == null
                || !controller.choose(outcome, choice, game)) {
            return false;
        }
        FilterCard filterCard = new FilterCard();
        filterCard.add(new ColorPredicate(choice.getColor()));
        targetPlayer.revealCards(source, targetPlayer.getHand(), game);
        targetPlayer.discard(new CardsImpl(targetPlayer.getHand().getCards(filterCard, game)), false, source, game);
        return true;
    }
}
