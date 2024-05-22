package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class EscapeAbility extends SpellAbility {

    private static final FilterCard filter = new FilterCard("card to exile");
    private static final String CASTED_WITH_ESCAPE_KEY = "escapeActivated";

    static {
        filter.add(AnotherPredicate.instance);
    }

    private final String staticText;

    public EscapeAbility(Card card, String manaCost, int exileCount) {
        this(card, manaCost, new CostsImpl<>(), exileCount);
    }

    public EscapeAbility(Card card, String manaCost, Costs<Cost> additionalCost) {
        this(card, manaCost, additionalCost, 0);
    }

    public EscapeAbility(Card card, String manaCost, Costs<Cost> additionalCosts, int exileCount) {
        super(card.getSpellAbility());
        this.newId();
        this.setCardName(card.getName() + " with Escape");
        this.zone = Zone.GRAVEYARD;
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;

        this.clearManaCosts();
        this.clearManaCostsToPay();

        this.addCost(new ManaCostsImpl<>(manaCost));
        for (Cost cost : additionalCosts) {
            this.addCost(cost.copy().setText("")); // hide additional cost text from rules
        }
        if (exileCount > 0) {
            this.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(exileCount, filter), "")); // hide additional cost text from rules
        }

        String text = "Escape&mdash;" + manaCost;
        for (Cost cost : additionalCosts) {
            text += ", " + CardUtil.getTextWithFirstCharUpperCase(cost.getText());
        }
        if (exileCount > 0) {
            text += ", Exile " + CardUtil.numberToText(exileCount) + " other cards from your graveyard";
        }
        text += ". <i>(You may cast this card from your graveyard for its escape cost.)</i>";
        this.staticText = text;
    }

    private EscapeAbility(final EscapeAbility ability) {
        super(ability);
        this.staticText = ability.staticText;
    }

    @Override
    public EscapeAbility copy() {
        return new EscapeAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return staticText;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (game.getState().getZone(getSourceId()) != Zone.GRAVEYARD) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (super.activate(game, noMana)) {
            game.getState().setValue(CASTED_WITH_ESCAPE_KEY + getSourceId().toString() + (getSourceObjectZoneChangeCounter() + 1), Boolean.TRUE);
            return true;
        }
        return false;
    }

    public static boolean wasCastedWithEscape(Game game, UUID sourceId, int sourceZCC) {
        Object activated = game.getState().getValue(CASTED_WITH_ESCAPE_KEY + sourceId.toString() + sourceZCC);
        return activated != null;
    }
}
