package mage.abilities.effects.common.cost;

import mage.ApprovingObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
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

    private final DynamicValue manaCost;

    /**
     * @param maxCost Maximum converted mana cost for this effect to apply to
     */
    public CastWithoutPayingManaCostEffect(int maxCost) {
        this(StaticValue.get(maxCost));
    }

    public CastWithoutPayingManaCostEffect(DynamicValue maxCost) {
        super(Outcome.PlayForFree);
        this.manaCost = maxCost;
        this.staticText = "you may cast a card with converted mana cost "
                + maxCost + " or less from your hand without paying its mana cost";
    }

    public CastWithoutPayingManaCostEffect(final CastWithoutPayingManaCostEffect effect) {
        super(effect);
        this.manaCost = effect.manaCost;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller == null) {
            return false;
        }
        int cmc = manaCost.calculate(game, source, this);
        FilterCard filter = new FilterNonlandCard("card with converted mana cost "
                + cmc + " or less from your hand");
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, cmc + 1));

        Target target = new TargetCardInHand(filter);
        if (target.canChoose(source.getSourceId(), controller.getId(), game)
                && controller.chooseUse(Outcome.PlayForFree, "Cast a card with converted mana cost " + cmc
                        + " or less from your hand without paying its mana cost?", source, game)) {
            Card cardToCast = null;
            boolean cancel = false;
            while (controller.canRespond() 
                    && !cancel) {
                if (controller.chooseTarget(Outcome.PlayForFree, target, source, game)) {
                    cardToCast = game.getCard(target.getFirstTarget());
                    if (cardToCast != null) {
                        if (cardToCast.getSpellAbility() == null) {
                            Logger.getLogger(CastWithoutPayingManaCostEffect.class).fatal("Card: "
                                    + cardToCast.getName() + " is no land and has no spell ability!");
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
                game.getState().setValue("PlayFromNotOwnHandZone" + cardToCast.getId(), Boolean.TRUE);
                controller.cast(controller.chooseAbilityForCast(cardToCast, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + cardToCast.getId(), null);
            }
        }
        return true;
    }

    @Override
    public CastWithoutPayingManaCostEffect copy() {
        return new CastWithoutPayingManaCostEffect(this);
    }
}
