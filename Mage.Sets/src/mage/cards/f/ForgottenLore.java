package mage.cards.f;

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
public final class ForgottenLore extends CardImpl {

    public ForgottenLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Target opponent chooses a card in your graveyard. You may pay {G}. If you do, repeat this process except that opponent can't choose a card already chosen for Forgotten Lore. Then put the last chosen card into your hand.
        this.getSpellAbility().addEffect(new ForgottenLoreEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private ForgottenLore(final ForgottenLore card) {
        super(card);
    }

    @Override
    public ForgottenLore copy() {
        return new ForgottenLore(this);
    }
}

class ForgottenLoreEffect extends OneShotEffect {

    public ForgottenLoreEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent chooses a card in your graveyard. You may pay {G}. If you do, repeat this process except that opponent can't choose a card already chosen for {this}. Then put the last chosen card into your hand.";
    }

    public ForgottenLoreEffect(final ForgottenLoreEffect effect) {
        super(effect);
    }

    @Override
    public ForgottenLoreEffect copy() {
        return new ForgottenLoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (you != null && opponent != null) {
            FilterCard filter = new FilterCard();
            filter.add(new OwnerIdPredicate(you.getId()));
            Cost cost = new ManaCostsImpl<>("{G}");
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
                        game.informPlayers("Forgotten Lore: " + opponent.getLogName() + " has chosen " + card.getLogName());
                    }
                } else {
                    done = true;
                }

                if (!done) {
                    if (cost.canPay(source, source, you.getId(), game) && you.chooseUse(Outcome.Benefit, "Pay {G} to choose a different card ?", source, game)) {
                        cost.clearPaid();
                        if (!cost.pay(source, game, source, you.getId(), false, null)) {
                            done = true;
                        }
                    } else {
                        done = true;
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
