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
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author notgreat
 */

//Mostly copied from Kicker/Multikicker code
public class SquadAbility extends StaticAbility implements OptionalAdditionalSourceCosts {
    protected Map<String, Integer> activations = new ConcurrentHashMap<>(); // zoneChangeCounter, activations
    protected OptionalAdditionalCost cost;
    protected static final String SQUAD_KEYWORD = "Squad";
    protected static final String SQUAD_REMINDER = "You may pay an additional "
            + "{cost} any number of times as you cast this spell.";
    public SquadAbility() {
        this(new GenericManaCost(2));
    }

    public SquadAbility(Cost cost) {
        super(Zone.STACK, null);
        addSubAbility(new EntersBattlefieldTriggeredAbility(new SquadEffectETB()));
        addSquadCost(cost);
    }

    private SquadAbility(final SquadAbility ability) {
        super(ability);
        this.activations.putAll(ability.activations);
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
        activations.clear();
    }

    protected int getSquadCount(Game game, Ability source) {
        String key;
        key = getActivationKey(source, game);
        if (activations.containsKey(key) && activations.get(key) > 0) {
            return activations.get(key);
        }
        return 0;
    }
    private static String getActivationKey(Ability source, Game game) {
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

        return zcc + "";
    }

    private void incrementSquad(Ability source, Game game) {
        int amount = 1;
        String key = getActivationKey(source, game);
        if (activations.containsKey(key)) {
            amount += activations.get(key);
        }
        activations.put(key, amount);
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
            int activatedCount = getSquadCount(game, ability);
            times = (activatedCount + 1) + (activatedCount == 0 ? " time " : " times ");
            // TODO: add AI support to find max number of possible activations (from available mana)
            //  canPay checks only single mana available, not total mana usage
            if (cost.canPay(ability, this, ability.getControllerId(), game)
                    && player.chooseUse(/*Outcome.Benefit*/Outcome.AIDontUseIt,
                    "Pay " + times + cost.getText(false) + " ?", ability, game)) {
                this.incrementSquad(ability, game);
                if (cost instanceof ManaCostsImpl) {
                    ability.getManaCostsToPay().add((ManaCostsImpl) cost.copy());
                } else {
                    ability.getCosts().add(cost.copy());
                }
            } else {
                again = false;
            }
        }
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
        int timesPaid = ((SquadAbility) game.getObject(source.getSourceId())).getSquadCount(game, source);
        CreateTokenCopySourceEffect effect = new CreateTokenCopySourceEffect(timesPaid);
        return effect.apply(game, source);
    }
}