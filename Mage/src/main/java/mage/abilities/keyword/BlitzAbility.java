package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.BlitzedCondition;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author TheElk801
 */
public class BlitzAbility extends StaticAbility implements AlternativeSourceCosts {

    protected static final String KEYWORD = "Blitz";
    protected static final String REMINDER_TEXT = "(If you cast this spell for its blitz cost, it gains haste " +
            "and \"When this creature dies, draw a card.\" Sacrifice it at the beginning of the next end step.)";

    protected List<AlternativeCost2> alternativeSourceCosts = new LinkedList<>();

    // needed to check activation status, if card changes zone after casting it
    private int zoneChangeCounter = 0;

    public BlitzAbility(String manaString) {
        super(Zone.ALL, null);
        name = KEYWORD;
        this.addBlitzCost(manaString);
        Ability ability = new EntersBattlefieldAbility(
                new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.Custom, false),
                BlitzedCondition.instance, "", ""
        );
        ability.addEffect(new GainAbilitySourceEffect(new DiesSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(1)
        ).setTriggerPhrase("When this creature dies, ")));
        ability.addEffect(new BlitzAddDelayedTriggeredAbilityEffect());
        ability.setRuleVisible(false);
        addSubAbility(ability);
    }

    private BlitzAbility(final BlitzAbility ability) {
        super(ability);
        this.alternativeSourceCosts.addAll(ability.alternativeSourceCosts);
        this.zoneChangeCounter = ability.zoneChangeCounter;
    }

    @Override
    public BlitzAbility copy() {
        return new BlitzAbility(this);
    }

    public final AlternativeCost2 addBlitzCost(String manaString) {
        AlternativeCost2 evokeCost = new AlternativeCost2Impl(KEYWORD, REMINDER_TEXT, new ManaCostsImpl(manaString));
        alternativeSourceCosts.add(evokeCost);
        return evokeCost;
    }

    public void resetBlitz() {
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
                this.resetBlitz();
                for (AlternativeCost2 BlitzCost : alternativeSourceCosts) {
                    if (BlitzCost.canPay(ability, this, player.getId(), game)
                            && player.chooseUse(Outcome.Benefit, KEYWORD
                            + " the creature for " + BlitzCost.getText(true) + " ?", ability, game)) {
                        activateBlitz(BlitzCost, game);
                        ability.getManaCostsToPay().clear();
                        ability.getCosts().clear();
                        for (Iterator it = ((Costs) BlitzCost).iterator(); it.hasNext(); ) {
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

    private void activateBlitz(AlternativeCost2 cost, Game game) {
        cost.activate();
        // remember zone change counter
        if (zoneChangeCounter == 0) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                zoneChangeCounter = card.getZoneChangeCounter(game);
            } else {
                throw new IllegalArgumentException("Blitz source card not found");
            }
        }
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        int numberCosts = 0;
        String remarkText = "";
        for (AlternativeCost2 BlitzCost : alternativeSourceCosts) {
            if (numberCosts == 0) {
                sb.append(BlitzCost.getText(false));
                remarkText = BlitzCost.getReminderText();
            } else {
                sb.append(" and/or ").append(BlitzCost.getText(true));
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

class BlitzAddDelayedTriggeredAbilityEffect extends OneShotEffect {

    public BlitzAddDelayedTriggeredAbilityEffect() {
        super(Outcome.Benefit);
    }

    public BlitzAddDelayedTriggeredAbilityEffect(final BlitzAddDelayedTriggeredAbilityEffect effect) {
        super(effect);
    }

    @Override
    public BlitzAddDelayedTriggeredAbilityEffect copy() {
        return new BlitzAddDelayedTriggeredAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getPermanentEntering(source.getSourceId()) != null) {
            // init target pointer now because the Blitzed creature will only be returned from battlefield zone (now in entering state so zone change counter is not raised yet)
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                    new SacrificeTargetEffect()
                            .setText("sacrifice the blitzed creature")
                            .setTargetPointer(new FixedTarget(source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId()) + 1))
            ), source);
            return true;
        }
        return false;
    }
}
