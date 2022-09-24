package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SharesCardTypePredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class Counterlash extends CardImpl {

    public Counterlash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Counter target spell. You may cast a nonland card in your hand 
        // that shares a card type with that spell without paying its mana cost.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterlashEffect());
    }

    private Counterlash(final Counterlash card) {
        super(card);
    }

    @Override
    public Counterlash copy() {
        return new Counterlash(this);
    }
}

class CounterlashEffect extends OneShotEffect {

    public CounterlashEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell. You may cast a spell "
                + "that shares a card type with it from your hand "
                + "without paying its mana cost";
    }

    public CounterlashEffect(final CounterlashEffect effect) {
        super(effect);
    }

    @Override
    public CounterlashEffect copy() {
        return new CounterlashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (stackObject == null || controller == null) {
            return false;
        }
        FilterCard filter = new FilterCard();
        filter.add(new SharesCardTypePredicate(stackObject.getCardType(game)));
        game.getStack().counter(source.getFirstTarget(), source, game);
        CardUtil.castSpellWithAttributesForFree(controller, source, game, new CardsImpl(controller.getHand()), filter);
        return true;
    }
}
