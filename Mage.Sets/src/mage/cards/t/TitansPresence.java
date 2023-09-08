
package mage.cards.t;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TitansPresence extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a colorless creature card from your hand");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public TitansPresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}");

        // As an additional cost to cast Titan's Presence, reveal a colorless creature card from your hand.
        this.getSpellAbility().addCost(new RevealTargetFromHandCost(new TargetCardInHand(filter)));

        // Exile target creature if its power is less than or equal to the revealed card's power.
        this.getSpellAbility().addEffect(new TitansPresenceEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TitansPresence(final TitansPresence card) {
        super(card);
    }

    @Override
    public TitansPresence copy() {
        return new TitansPresence(this);
    }
}

class TitansPresenceEffect extends OneShotEffect {

    public TitansPresenceEffect() {
        super(Outcome.Exile);
        staticText = "Exile target creature if its power is less than or equal to the revealed card's power";
    }

    private TitansPresenceEffect(final TitansPresenceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = (RevealTargetFromHandCost) source.getCosts().get(0);
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (cost != null && creature != null && controller != null) {
            List<Card> revealedCards = cost.getRevealedCards();
            if (!revealedCards.isEmpty()) {
                Card card = revealedCards.iterator().next();
                if (card != null && card.getPower().getValue() >= creature.getPower().getValue()) {
                    controller.moveCards(creature, Zone.EXILED, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public TitansPresenceEffect copy() {
        return new TitansPresenceEffect(this);
    }

}
