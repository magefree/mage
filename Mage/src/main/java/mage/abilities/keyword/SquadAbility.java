package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.Effect;
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
    protected static final String SQUAD_REMINDER = "You may pay an additional "
            + "{cost} any number of times as you cast this spell.";
    public SquadAbility() {
        this(new GenericManaCost(2));
    }

    public SquadAbility(Cost cost) {
        super(Zone.STACK, null);
        setSquadCost(cost);
        addSubAbility(new SquadTriggerAbility());
        //Note that I get subabilities list's position 0 to modify the zcc/count references
    }

    private SquadAbility(final SquadAbility ability) {
        super(ability);
        this.cost = ability.cost.copy();
    }

    @Override
    public SquadAbility copy() {
        return new SquadAbility(this);
    }

    public final void setSquadCost(Cost cost) {
        OptionalAdditionalCost newCost = new OptionalAdditionalCostImpl(
                SQUAD_KEYWORD, SQUAD_REMINDER, cost);
        newCost.setRepeatable(true);
        newCost.setCostType(VariableCostType.ADDITIONAL);
        this.cost = newCost;
    }

    private void reset() {
        cost.reset();
    }

    protected static int get_zcc(Ability source, Game game) {
        // Squad/Kicker activates in STACK zone so all zcc must be from "stack moment"
        // Use cases:
        // * resolving spell have same zcc (example: check kicker status in sorcery/instant);
        // * copied spell have same zcc as source spell (see Spell.copySpell and zcc sync);
        // * creature/token from resolved spell have +1 zcc after moved to battlefield (example: check kicker status in ETB triggers/effects);

        // find object info from the source ability (it can be a permanent or a spell on stack, on the moment of trigger/resolve)
        MageObject sourceObject = source.getSourceObject(game);
        Zone sourceObjectZone = game.getState().getZone(sourceObject.getId());
        int zcc = CardUtil.getActualSourceObjectZoneChangeCounter(game, source);

        // find "stack moment" zcc:
        // * permanent cards enters from STACK to BATTLEFIELD (+1 zcc)
        // * permanent tokens enters from OUTSIDE to BATTLEFIELD (+1 zcc, see prepare code in TokenImpl.putOntoBattlefieldHelper)
        // * spells and copied spells resolves on STACK (zcc not changes)
        if (sourceObjectZone != Zone.STACK) {
            --zcc;
        }
        return zcc;
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
            int activatedCount = getSquadCount();
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
        SquadTriggerAbility squadETB = (SquadTriggerAbility)getSubAbilities().get(0);
        squadETB.zcc = get_zcc(ability, game);
        SquadEffectETB squadEffect = (SquadEffectETB)squadETB.getEffects().get(0);
        squadEffect.activationCount = cost.getActivateCount();
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

    /**
     * Number of times squad cost was paid
     *
     * @return int activation count
     */
    public int getSquadCount() {
        return cost.getActivateCount();
    }
}
class SquadTriggerAbility extends EntersBattlefieldTriggeredAbility {
    protected Integer zcc;
    public SquadTriggerAbility() {
        super(new SquadEffectETB());
        this.setRuleVisible(false);
    }

    private SquadTriggerAbility(final SquadTriggerAbility ability) {
        super(ability);
        this.zcc = ability.zcc;
    }
    @Override
    public SquadTriggerAbility copy() {
        return new SquadTriggerAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        if (zcc != null && zcc == SquadAbility.get_zcc(this, game)){
            SquadEffectETB effect = (SquadEffectETB)getEffects().get(0);
            return effect.activationCount > 0;
        }
        return false;
    }
    @Override
    public String getRule() {
        return "Squad <i>(When this creature enters the battlefield, if its squad cost was paid, "
                + "create a token that’s a copy of it for each time its squad cost was paid.)</i>";
    }
}

class SquadEffectETB extends OneShotEffect {
    protected Integer activationCount;

    SquadEffectETB() {
        super(Outcome.Benefit);
    }

    private SquadEffectETB(final SquadEffectETB effect) {
        super(effect);
        this.activationCount = effect.activationCount;
    }

    @Override
    public SquadEffectETB copy() {
        return new SquadEffectETB(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (activationCount != null) {
            CreateTokenCopySourceEffect effect = new CreateTokenCopySourceEffect(activationCount);
            return effect.apply(game, source);
        }
        return true;
    }
}
