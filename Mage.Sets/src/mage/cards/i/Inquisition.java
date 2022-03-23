package mage.cards.i;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author jeffwadsworth & L_J
 */
public final class Inquisition extends CardImpl {

    public Inquisition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player reveals their hand. Inquisition deals damage to that player equal to the number of white cards in their hand.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new InquisitionEffect());
    }

    private Inquisition(final Inquisition card) {
        super(card);
    }

    @Override
    public Inquisition copy() {
        return new Inquisition(this);
    }
}

class InquisitionEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public InquisitionEffect() {
        super(Outcome.Exile);
        staticText = "Target player reveals their hand. Inquisition deals damage to that player equal to the number of white cards in their hand";
    }

    public InquisitionEffect(final InquisitionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && controller != null) {
            Player targetPlayer = game.getPlayer(source.getFirstTarget());
            if (targetPlayer != null) {
                targetPlayer.revealCards("hand of " + targetPlayer.getName(), targetPlayer.getHand(), game);
                int cardsFound = 0;
                for (Card card : targetPlayer.getHand().getCards(game)) {
                    if (filter.match(card, game)) {
                        cardsFound++;
                    }
                }
                if (cardsFound > 0) {
                    targetPlayer.damage(cardsFound, source.getSourceId(), source, game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public InquisitionEffect copy() {
        return new InquisitionEffect(this);
    }
}
