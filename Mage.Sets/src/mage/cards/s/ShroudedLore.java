package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class ShroudedLore extends CardImpl {

    public ShroudedLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Target opponent chooses a card in your graveyard. You may pay {B}. If you do, repeat this process except that opponent can't choose a card already chosen for Shrouded Lore. Then put the last chosen card into your hand.
        this.getSpellAbility().addEffect(new ShroudedLoreEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private ShroudedLore(final ShroudedLore card) {
        super(card);
    }

    @Override
    public ShroudedLore copy() {
        return new ShroudedLore(this);
    }
}

class ShroudedLoreEffect extends OneShotEffect {

    public ShroudedLoreEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent chooses a card in your graveyard. You may pay {B}. If you do, repeat this process except that opponent can't choose a card already chosen for {this}. Then put the last chosen card into your hand.";
    }

    public ShroudedLoreEffect(final ShroudedLoreEffect effect) {
        super(effect);
    }

    @Override
    public ShroudedLoreEffect copy() {
        return new ShroudedLoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (you != null && opponent != null) {
            FilterCard filter = new FilterCard();
            filter.add(new OwnerIdPredicate(you.getId()));
            Cost cost = new ManaCostsImpl("{B}");
            TargetCardInGraveyard chosenCard;
            Card card = null;
            boolean done = false;
            do {
                chosenCard = new TargetCardInGraveyard(filter);
                chosenCard.setNotTarget(true);
                if (chosenCard.canChoose(opponent.getId(), source, game)) {
                    opponent.chooseTarget(Outcome.ReturnToHand, chosenCard, source, game);
                    card = game.getCard(chosenCard.getFirstTarget());
                    if (card != null) {
                        filter.add(Predicates.not(new CardIdPredicate(card.getId())));
                        game.informPlayers("Shrouded Lore: " + opponent.getLogName() + " has chosen " + card.getLogName());
                    }
                } else {
                    done = true;
                }

                if (!done) {
                    done = true;
                    if (cost.canPay(source, source, you.getId(), game) && you.chooseUse(Outcome.Benefit, "Pay {B} to choose a different card ?", source, game)) {
                        cost.clearPaid();
                        if (cost.pay(source, game, source, you.getId(), false, null)) {
                            done = false;
                        }
                    }
                }

            } while (!done);

            if (card != null) {
                Cards cardsToHand = new CardsImpl();
                cardsToHand.add(card);
                you.moveCards(cardsToHand, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
