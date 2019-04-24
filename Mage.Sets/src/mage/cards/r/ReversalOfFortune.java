
package mage.cards.r;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public final class ReversalOfFortune extends CardImpl {

    public ReversalOfFortune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Target opponent reveals their hand. You may copy an instant or sorcery card in it. If you do, you may cast the copy without paying its mana cost.
        this.getSpellAbility().addEffect(new ReversalOfFortuneEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public ReversalOfFortune(final ReversalOfFortune card) {
        super(card);
    }

    @Override
    public ReversalOfFortune copy() {
        return new ReversalOfFortune(this);
    }
}

class ReversalOfFortuneEffect extends OneShotEffect {

    public ReversalOfFortuneEffect() {
        super(Outcome.Copy);
        this.staticText = "Target opponent reveals their hand. You may copy an instant or sorcery card in it. If you do, you may cast the copy without paying its mana cost";
    }

    public ReversalOfFortuneEffect(final ReversalOfFortuneEffect effect) {
        super(effect);
    }

    @Override
    public ReversalOfFortuneEffect copy() {
        return new ReversalOfFortuneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller != null && opponent != null) {
            // Target opponent reveals their hand
            Cards revealedCards = new CardsImpl();
            revealedCards.addAll(opponent.getHand());
            opponent.revealCards("Reveal", revealedCards, game);

            //You may copy an instant or sorcery card in it
            TargetCard target = new TargetCard(1, Zone.HAND, new FilterInstantOrSorceryCard());
            target.setRequired(false);
            if (controller.choose(outcome, revealedCards, target, game)) {
                Card card = revealedCards.get((UUID) target.getFirstTarget(), game);
                //If you do, you may cast the copy without paying its mana cost
                if (card != null) {
                    Card copiedCard = game.copyCard(card, source, source.getControllerId());
                    if (controller.chooseUse(outcome, "Cast the copied card without paying mana cost?", source, game)) {
                        controller.cast(copiedCard.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
