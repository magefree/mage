package mage.abilities.keyword;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.DashedCondition;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.costs.AlternativeCost2;
import mage.abilities.costs.AlternativeCost2Impl;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class DashAbility extends StaticAbility implements AlternativeSourceCosts {

    protected static final String KEYWORD = "Dash";
    protected static final String REMINDER_TEXT = "(You may cast this spell for its dash cost. "
            + "If you do, it gains haste, and it's returned from the battlefield to its owner's "
            + "hand at the beginning of the next end step.)";

    protected List<AlternativeCost2> alternativeSourceCosts = new LinkedList<>();

    // needed to check activation status, if card changes zone after casting it
    private int zoneChangeCounter = 0;

    public DashAbility(Card card, String manaString) {
        super(Zone.ALL, null);
        name = KEYWORD;
        this.addDashCost(manaString);
        Ability ability = new EntersBattlefieldAbility(
                new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.Custom, false),
                DashedCondition.instance, "", "");
        ability.addEffect(new DashAddDelayedTriggeredAbilityEffect());
        ability.setRuleVisible(false);
        addSubAbility(ability);

    }

    public DashAbility(final DashAbility ability) {
        super(ability);
        this.alternativeSourceCosts.addAll(ability.alternativeSourceCosts);
        this.zoneChangeCounter = ability.zoneChangeCounter;
    }

    @Override
    public DashAbility copy() {
        return new DashAbility(this);
    }

    public final AlternativeCost2 addDashCost(String manaString) {
        AlternativeCost2 evokeCost = new AlternativeCost2Impl(KEYWORD, REMINDER_TEXT, new ManaCostsImpl(manaString));
        alternativeSourceCosts.add(evokeCost);
        return evokeCost;
    }

    public void resetDash() {
        for (AlternativeCost2 cost : alternativeSourceCosts) {
            cost.reset();
        }
        zoneChangeCounter = 0;
    }

    @Override
    public boolean isActivated(Ability ability, Game game) {
        Card card = game.getCard(sourceId);
        if (card != null
                && card.getZoneChangeCounter(game) <= zoneChangeCounter + 1) {
            for (AlternativeCost2 cost : alternativeSourceCosts) {
                if (cost.isActivated(game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isAvailable(Ability source, Game game) {
        return true;
    }

    @Override
    public boolean askToActivateAlternativeCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            // we must use the controller of the ability here IE: Hedonist's Trove (play from not own hand when you aren't the owner)
            Player player = game.getPlayer(ability.getControllerId());
            if (player != null) {
                this.resetDash();
                for (AlternativeCost2 dashCost : alternativeSourceCosts) {
                    if (dashCost.canPay(ability, this, controllerId, game)
                            && player.chooseUse(Outcome.Benefit, KEYWORD
                                    + " the creature for " + dashCost.getText(true) + " ?", ability, game)) {
                        activateDash(dashCost, game);
                        ability.getManaCostsToPay().clear();
                        ability.getCosts().clear();
                        for (Iterator it = ((Costs) dashCost).iterator(); it.hasNext();) {
                            Cost cost = (Cost) it.next();
                            if (cost instanceof ManaCostsImpl) {
                                ability.getManaCostsToPay().add((ManaCostsImpl) cost.copy());
                            } else {
                                ability.getCosts().add(cost.copy());
                            }
                        }
                    }
                }
            }
        }
        return isActivated(ability, game);
    }

    private void activateDash(AlternativeCost2 cost, Game game) {
        cost.activate();
        // remember zone change counter
        if (zoneChangeCounter == 0) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                zoneChangeCounter = card.getZoneChangeCounter(game);
            } else {
                throw new IllegalArgumentException("Dash source card not found");
            }
        }
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        int numberCosts = 0;
        String remarkText = "";
        for (AlternativeCost2 dashCost : alternativeSourceCosts) {
            if (numberCosts == 0) {
                sb.append(dashCost.getText(false));
                remarkText = dashCost.getReminderText();
            } else {
                sb.append(" and/or ").append(dashCost.getText(true));
            }
            ++numberCosts;
        }
        if (numberCosts == 1) {
            sb.append(' ').append(remarkText);
        }

        return sb.toString();
    }

    @Override
    public String getCastMessageSuffix(Game game) {
        StringBuilder sb = new StringBuilder();
        int position = 0;
        for (AlternativeCost2 cost : alternativeSourceCosts) {
            if (cost.isActivated(game)) {
                sb.append(cost.getCastSuffixMessage(position));
                ++position;
            }
        }
        return sb.toString();
    }

    @Override
    public Costs<Cost> getCosts() {
        Costs<Cost> alterCosts = new CostsImpl<>();
        for (AlternativeCost2 aCost : alternativeSourceCosts) {
            alterCosts.add(aCost.getCost());
        }
        return alterCosts;
    }
}

class DashAddDelayedTriggeredAbilityEffect extends OneShotEffect {

    public DashAddDelayedTriggeredAbilityEffect() {
        super(Outcome.Benefit);
        this.staticText = "return the dashed creature from the battlefield to its owner's hand";
    }

    public DashAddDelayedTriggeredAbilityEffect(final DashAddDelayedTriggeredAbilityEffect effect) {
        super(effect);
    }

    @Override
    public DashAddDelayedTriggeredAbilityEffect copy() {
        return new DashAddDelayedTriggeredAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getPermanentEntering(source.getSourceId()) != null) {
            OneShotEffect returnToHandEffect = new ReturnToHandTargetEffect();
            ConditionalOneShotEffect mustBeOnBattlefieldToReturn = new ConditionalOneShotEffect(returnToHandEffect, SourceOnBattlefieldCondition.instance);
            mustBeOnBattlefieldToReturn.setText("return the dashed creature from the battlefield to its owner's hand");
            // init target pointer now because the dashed creature will only be returned from battlefield zone (now in entering state so zone change counter is not raised yet)
            mustBeOnBattlefieldToReturn.setTargetPointer(new FixedTarget(source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId()) + 1));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(mustBeOnBattlefieldToReturn);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}
