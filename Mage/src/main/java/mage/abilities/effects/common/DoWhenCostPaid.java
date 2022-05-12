package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

public class DoWhenCostPaid extends OneShotEffect {

    private final ReflexiveTriggeredAbility ability;
    private final Cost cost;
    private final String chooseUseText;
    private final boolean optional;

    public DoWhenCostPaid(ReflexiveTriggeredAbility ability, Cost cost, String chooseUseText) {
        this(ability, cost, chooseUseText, true);
    }

    public DoWhenCostPaid(ReflexiveTriggeredAbility ability, Cost cost, String chooseUseText, boolean optional) {
        super(Outcome.Benefit);
        this.ability = ability;
        this.cost = cost;
        this.chooseUseText = chooseUseText;
        this.optional = optional;
    }

    private DoWhenCostPaid(final DoWhenCostPaid effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.cost = effect.cost.copy();
        this.chooseUseText = effect.chooseUseText;
        this.optional = effect.optional;
    }

    private boolean checkOptional(Player player, Game game, Ability source) {
        return optional && !player.chooseUse(
                ability.getEffects().getOutcome(source, this.outcome),
                CardUtil.replaceSourceName(
                        chooseUseText, CardUtil.getSourceName(game, source)
                ), source, game
        );
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (!cost.canPay(source, source, player.getId(), game)
                || checkOptional(player, game, source)) {
            return false;
        }
        cost.clearPaid();
        int bookmark = game.bookmarkState();
        if (cost.pay(source, game, source, player.getId(), false)) {
            if (ability.getTargets().isEmpty()) {
                ability.getEffects().setTargetPointer(getTargetPointer());
            }
            game.fireReflexiveTriggeredAbility(ability, source);
            player.resetStoredBookmark(game);
            return true;
        }
        player.restoreState(bookmark, DoWhenCostPaid.class.getName(), game);
        return true;
    }

    public Cost getCost() {
        return cost;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        return (optional ? "you may " : "")
                + CardUtil.addCostVerb(cost.getText())
                + ". When you do, "
                + CardUtil.getTextWithFirstCharLowerCase(ability.getRule());
    }

    @Override
    public void setValue(String key, Object value) {
        super.setValue(key, value);
        ability.getEffects().setValue(key, value);
    }

    @Override
    public DoWhenCostPaid copy() {
        return new DoWhenCostPaid(this);
    }
}
