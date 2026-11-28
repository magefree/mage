package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author JayDi85
 */
public class GainMiracleAbilityAllEffect extends ContinuousEffectImpl {

    private static final String REMINDER_TEXT = " <i>(You may cast a card for its miracle cost when you draw it if it's the first card you drew this turn.)</i>";

    private final FilterCard filter;
    private final String staticCost;
    private final int reducedByCost;

    private GainMiracleAbilityAllEffect(FilterCard filter, String staticCost, int reducedByCost, String text) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.filter = filter;
        this.staticCost = staticCost;
        this.reducedByCost = reducedByCost;
        this.staticText = text;
    }

    protected GainMiracleAbilityAllEffect(final GainMiracleAbilityAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.staticCost = effect.staticCost;
        this.reducedByCost = effect.reducedByCost;
    }

    @Override
    public GainMiracleAbilityAllEffect copy() {
        return new GainMiracleAbilityAllEffect(this);
    }

    public static Ability makeAbility(FilterCard filter, String staticCost) {
        String text = makeDefaultTextPrefix(filter) + " " + staticCost + "." + REMINDER_TEXT;
        Ability ability = new SimpleStaticAbility(new GainMiracleAbilityAllEffect(filter, staticCost, 0, text));
        ability.addSubAbility(MiracleAbility.makeExternalDrawAbility(filter, staticCost, text));
        return ability;
    }

    public static Ability makeAbility(FilterCard filter, int reducedByCost) {
        String text = makeDefaultTextPrefix(filter) + ". Its miracle cost is equal to its mana cost reduced by {" + reducedByCost + "}." + REMINDER_TEXT;
        Ability ability = new SimpleStaticAbility(new GainMiracleAbilityAllEffect(filter, null, reducedByCost, text));
        ability.addSubAbility(MiracleAbility.makeExternalDrawAbility(filter, reducedByCost, text));
        return ability;
    }

    private static String makeDefaultTextPrefix(FilterCard filter) {
        String filterMsg = filter.getMessage();
        boolean plural = filterMsg.endsWith("s");
        return plural
                ? CardUtil.getTextWithFirstCharUpperCase(filterMsg) + " in your hand have miracle"
                : "Each " + filterMsg + " in your hand has miracle";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (Card card : controller.getHand().getCards(filter, source.getControllerId(), source, game)) {
            String costText = staticCost != null
                    ? staticCost
                    : MiracleAbility.getReducedMiracleCostText(card, reducedByCost);
            game.getState().addOtherAbility(card, new MiracleAbility(costText));
        }
        return true;
    }
}
