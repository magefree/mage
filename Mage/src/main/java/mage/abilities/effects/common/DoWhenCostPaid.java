package mage.abilities.effects.common;

import mage.MageObject;
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

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (player == null || mageObject == null) {
            return false;
        }
        String message = CardUtil.replaceSourceName(chooseUseText, mageObject.getLogName());
        Outcome payOutcome = ability.getEffects().getOutcome(source, this.outcome);
        if (!cost.canPay(source, source, player.getId(), game)
                || (optional && !player.chooseUse(payOutcome, message, source, game))) {
            return false;
        }
        cost.clearPaid();
        int bookmark = game.bookmarkState();
        if (cost.pay(source, game, source, player.getId(), false)) {
            ability.getEffects().setTargetPointer(getTargetPointer());
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
        return (optional ? "you may " : "") + getCostText() + ". When you do, " + CardUtil.getTextWithFirstCharLowerCase(ability.getRule());
    }

    private String getCostText() {
        StringBuilder sb = new StringBuilder();
        String costText = cost.getText();
        if (!CardUtil.checkCostWords(costText)) {
            sb.append("pay ");
        }
        return sb.append(costText).toString();
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
