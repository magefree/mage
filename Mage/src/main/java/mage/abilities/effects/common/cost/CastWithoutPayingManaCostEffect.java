
package mage.abilities.effects.common.cost;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import org.apache.log4j.Logger;

/**
 * @author fireshoes - Original Code
 * @author JRHerlehy - Implement as seperate class
 * <p>
 * Allows player to choose to cast as card from hand without paying its mana
 * cost.
 * </p>
 */
public class CastWithoutPayingManaCostEffect extends OneShotEffect {

    private final FilterNonlandCard filter;
    private final int manaCost;

    /**
     * @param maxCost Maximum converted mana cost for this effect to apply to
     */
    public CastWithoutPayingManaCostEffect(int maxCost) {
        super(Outcome.PlayForFree);
        filter = new FilterNonlandCard("card with converted mana cost " + maxCost + " or less from your hand");
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, maxCost + 1));
        this.manaCost = maxCost;
        this.staticText = "you may cast a card with converted mana cost " + maxCost + " or less from your hand without paying its mana cost";
    }

    public CastWithoutPayingManaCostEffect(final CastWithoutPayingManaCostEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.manaCost = effect.manaCost;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller == null) {
            return false;
        }

        Target target = new TargetCardInHand(filter);
        if (target.canChoose(source.getSourceId(), controller.getId(), game)
                && controller.chooseUse(outcome, "Cast a card with converted mana cost " + manaCost
                        + " or less from your hand without paying its mana cost?", source, game)) {
            Card cardToCast = null;
            boolean cancel = false;
            while (controller.canRespond() && !cancel) {
                if (controller.chooseTarget(outcome, target, source, game)) {
                    cardToCast = game.getCard(target.getFirstTarget());
                    if (cardToCast != null) {
                        if (cardToCast.getSpellAbility() == null) {
                            Logger.getLogger(CastWithoutPayingManaCostEffect.class).fatal("Card: " + cardToCast.getName() + " is no land and has no spell ability!");
                            cancel = true;
                        }
                        if (cardToCast.getSpellAbility().canChooseTarget(game)) {
                            cancel = true;
                        }
                    }
                } else {
                    cancel = true;
                }
            }
            if (cardToCast != null) {
                controller.cast(cardToCast.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
            }
        }
        return true;
    }

    @Override
    public CastWithoutPayingManaCostEffect copy() {
        return new CastWithoutPayingManaCostEffect(this);
    }
}
