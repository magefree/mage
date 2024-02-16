package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author notgreat
 */

public class SquadAbility extends StaticAbility implements OptionalAdditionalSourceCosts {
    protected OptionalAdditionalCost cost;
    protected static final String SQUAD_KEYWORD = "Squad";
    protected static final String SQUAD_ACTIVATION_VALUE_KEY = "squadActivationCount";
    protected static final String SQUAD_REMINDER = "You may pay an additional "
            + "{cost} any number of times as you cast this spell.";
    public SquadAbility() {
        this(new GenericManaCost(2));
    }

    public SquadAbility(Cost cost) {
        super(Zone.STACK, null);
        setSquadCost(cost);
        addSubAbility(new SquadTriggerAbility());
    }

    private SquadAbility(final SquadAbility ability) {
        super(ability);
        this.cost = ability.cost.copy();
    }

    @Override
    public SquadAbility copy() {
        return new SquadAbility(this);
    }

    private void setSquadCost(Cost cost) {
        OptionalAdditionalCost newCost = new OptionalAdditionalCostImpl(
                SQUAD_KEYWORD, SQUAD_REMINDER, cost);
        newCost.setRepeatable(true);
        newCost.setCostType(VariableCostType.ADDITIONAL);
        this.cost = newCost;
    }

    private void reset() {
        cost.reset();
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (!(ability instanceof SpellAbility)) {
            return;
        }
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return;
        }
        this.reset();
        boolean again = true;
        while (player.canRespond() && again) {
            String times = "";
            int activatedCount = cost.getActivateCount();
            times = (activatedCount + 1) + (activatedCount == 0 ? " time " : " times ");
            // TODO: add AI support to find max number of possible activations (from available mana)
            //  canPay checks only single mana available, not total mana usage
            if (cost.canPay(ability, this, ability.getControllerId(), game)
                    && player.chooseUse(/*Outcome.Benefit*/Outcome.AIDontUseIt,
                    "Pay " + times + cost.getText(false) + " ?", ability, game)) {
                cost.activate();
                if (cost instanceof ManaCostsImpl) {
                    ability.getManaCostsToPay().add((ManaCostsImpl) cost.copy());
                } else {
                    ability.getCosts().add(cost.copy());
                }
            } else {
                again = false;
            }
        }
        ability.setCostsTag(SQUAD_ACTIVATION_VALUE_KEY,cost.getActivateCount());
    }

    @Override
    public String getCastMessageSuffix() {
        if (cost.isActivated()) {
            return cost.getCastSuffixMessage(0);
        }
        return "";
    }

    @Override
    public String getRule() {
        return "Squad "+cost.getText()+" <i>(As an additional cost to cast this spell, you may pay "+
            cost.getText()+"any number of times. When this creature enters the battlefield, "+
            "create that many tokens that are copies of it.)</i>";
    }
}
class SquadTriggerAbility extends EntersBattlefieldTriggeredAbility {
    public SquadTriggerAbility() {
        super(new SquadEffectETB());
        this.setRuleVisible(false);
    }

    private SquadTriggerAbility(final SquadTriggerAbility ability) {
        super(ability);
    }
    @Override
    public SquadTriggerAbility copy() {
        return new SquadTriggerAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        int squadCount = CardUtil.getSourceCostsTag(game, this, SquadAbility.SQUAD_ACTIVATION_VALUE_KEY,0);
        return (squadCount > 0);
    }
    @Override
    public String getRule() {
        return "Squad <i>(When this creature enters the battlefield, if its squad cost was paid, "
                + "create a token that's a copy of it for each time its squad cost was paid.)</i>";
    }
}

class SquadEffectETB extends OneShotEffect {

    SquadEffectETB() {
        super(Outcome.Benefit);
    }

    private SquadEffectETB(final SquadEffectETB effect) {
        super(effect);
    }

    @Override
    public SquadEffectETB copy() {
        return new SquadEffectETB(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int squadCount = CardUtil.getSourceCostsTag(game, source, SquadAbility.SQUAD_ACTIVATION_VALUE_KEY,0);
        CreateTokenCopySourceEffect effect = new CreateTokenCopySourceEffect(squadCount);
        return effect.apply(game, source);
    }
}
