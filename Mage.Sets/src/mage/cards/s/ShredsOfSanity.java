
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class ShredsOfSanity extends CardImpl {

    private final static FilterCard filterInstant = new FilterCard("an instant card in your graveyard");
    private final static FilterCard filterSorcery = new FilterCard("a sorcery card in your graveyard");

    static {
        filterInstant.add(new CardTypePredicate(CardType.INSTANT));
        filterSorcery.add(new CardTypePredicate(CardType.SORCERY));
    }

    public ShredsOfSanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Return up to one target instant card and up to one target sorcery card from your graveyard to your hand, then discard a card. Exile Shreds of Sanity.
        this.getSpellAbility().addEffect(new ShredsOfSanityEffect());
        this.getSpellAbility().addTarget(new TargetCard(0, 1, Zone.GRAVEYARD, filterInstant));
        this.getSpellAbility().addTarget(new TargetCard(0, 1, Zone.GRAVEYARD, filterSorcery));
        this.getSpellAbility().addEffect(new ExileSourceEffect());
    }

    public ShredsOfSanity(final ShredsOfSanity card) {
        super(card);
    }

    @Override
    public ShredsOfSanity copy() {
        return new ShredsOfSanity(this);
    }
}

class ShredsOfSanityEffect extends OneShotEffect {

    public ShredsOfSanityEffect() {
        super(Outcome.Benefit);
        this.staticText = "return up to one target instant card and up to one target sorcery card from your graveyard to your hand, then discard a card";
    }

    public ShredsOfSanityEffect(final ShredsOfSanityEffect effect) {
        super(effect);
    }

    @Override
    public ShredsOfSanityEffect copy() {
        return new ShredsOfSanityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToHand = new CardsImpl();
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                cardsToHand.add(card);
            }
            card = game.getCard(source.getTargets().get(1).getFirstTarget());
            if (card != null) {
                cardsToHand.add(card);
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            controller.discardOne(false, source, game);
            return true;
        }
        return false;
    }
}
