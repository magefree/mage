package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.condition.common.SquadCondition;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
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
    protected static final String SQUAD_REMINDER = "You may pay an additional "
            + "{cost} any number of times as you cast this spell.";
    public SquadAbility() {
        this(new GenericManaCost(2));
    }

    public SquadAbility(Cost cost) {
        super(Zone.STACK, null);
        addSubAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new SquadEffectETB()),
                SquadCondition.instance, "“When this creature enters the battlefield, if its squad cost was paid, " +
                    "create a token that’s a copy of it for each time its squad cost was paid."
        ));
        addSquadCost(cost);
    }

    private SquadAbility(final SquadAbility ability) {
        super(ability);
        this.cost = ability.cost.copy();
    }

    @Override
    public SquadAbility copy() {
        return new SquadAbility(this);
    }

    public final void addSquadCost(Cost cost) {
        OptionalAdditionalCost newCost = new OptionalAdditionalCostImpl(
                SQUAD_KEYWORD, SQUAD_REMINDER, cost);
        newCost.setRepeatable(true);
        newCost.setCostType(VariableCostType.ADDITIONAL);
        this.cost = newCost;
    }

    private void reset() {
        cost.reset();
    }

    protected static String getActivationKey(Ability source, Game game) {
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

        return "Squad_zcc"+zcc;
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
        int activatedCount = 0;
        while (player.canRespond() && again) {
            String times = "";
            times = (activatedCount + 1) + (activatedCount == 0 ? " time " : " times ");
            // TODO: add AI support to find max number of possible activations (from available mana)
            //  canPay checks only single mana available, not total mana usage
            if (cost.canPay(ability, this, ability.getControllerId(), game)
                    && player.chooseUse(/*Outcome.Benefit*/Outcome.AIDontUseIt,
                    "Pay " + times + cost.getText(false) + " ?", ability, game)) {
                activatedCount += 1;
                if (cost instanceof ManaCostsImpl) {
                    ability.getManaCostsToPay().add((ManaCostsImpl) cost.copy());
                } else {
                    ability.getCosts().add(cost.copy());
                }
            } else {
                again = false;
            }
        }
        this.getSubAbilities().get(0).getEffects().setValue(getActivationKey(ability, game),activatedCount);
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
        return "Squad";
    }

    /**
     * If squad cost was paid
     *
     * @param game
     * @param source
     * @return boolean if the squad effect was paid
     */
    public boolean isPaid(Game game, Ability source) {
        String activationKey = getActivationKey(source, game);
        Object val = this.getSubAbilities().get(0).getAllEffects().get(0).getValue(activationKey);
        return val != null && (int) val > 0;
    }
}
class SquadEffectETB extends OneShotEffect {

    SquadEffectETB() {
        super(Outcome.Benefit);
        staticText = "create that many tokens that are copies of it.";
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
        String activationKey = SquadAbility.getActivationKey(source, game);
        CreateTokenCopySourceEffect effect = new CreateTokenCopySourceEffect((int)getValue(activationKey));
        return effect.apply(game, source);
    }
}
