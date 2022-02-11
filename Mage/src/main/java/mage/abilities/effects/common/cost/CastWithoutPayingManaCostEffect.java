package mage.abilities.effects.common.cost;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

/**
 * @author fireshoes - Original Code
 * @author JRHerlehy - Implement as seperate class
 * <p>
 * Allows player to choose to cast as card from hand without paying its mana
 * cost.
 * </p>
 * TODO: this doesn't work correctly with MDFCs or Adventures (see https://github.com/magefree/mage/issues/7742)
 */
public class CastWithoutPayingManaCostEffect extends OneShotEffect {

    private final DynamicValue manaCost;
    private final FilterCard filter;
    private static final FilterCard defaultFilter
            = new FilterNonlandCard("card with mana value %mv or less from your hand");

    /**
     * @param maxCost Maximum converted mana cost for this effect to apply to
     */
    public CastWithoutPayingManaCostEffect(int maxCost) {
        this(StaticValue.get(maxCost));
    }

    public CastWithoutPayingManaCostEffect(DynamicValue maxCost) {
        this(maxCost, defaultFilter);
    }

    public CastWithoutPayingManaCostEffect(DynamicValue maxCost, FilterCard filter) {
        super(Outcome.PlayForFree);
        this.manaCost = maxCost;
        this.filter = filter;
        this.staticText = "you may cast a spell with mana value "
                + maxCost + " or less from your hand without paying its mana cost";
    }

    public CastWithoutPayingManaCostEffect(final CastWithoutPayingManaCostEffect effect) {
        super(effect);
        this.manaCost = effect.manaCost;
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int cmc = manaCost.calculate(game, source, this);
        FilterCard filter = this.filter.copy();
        filter.setMessage(filter.getMessage().replace("%mv", "" + cmc));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, cmc + 1));
        Target target = new TargetCardInHand(filter);
        if (!target.canChoose(
                source.getSourceId(), controller.getId(), game
        ) || !controller.chooseUse(
                Outcome.PlayForFree,
                "Cast " + CardUtil.addArticle(filter.getMessage())
                        + " without paying its mana cost?", source, game
        )) {
            return true;
        }
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
                    if (cardToCast.getSpellAbility().canChooseTarget(game, controller.getId())) {
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
        return true;
    }

    @Override
    public CastWithoutPayingManaCostEffect copy() {
        return new CastWithoutPayingManaCostEffect(this);
    }
}
