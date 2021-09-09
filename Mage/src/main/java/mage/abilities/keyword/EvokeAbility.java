package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.EvokedCondition;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author LevelX2
 */
public class EvokeAbility extends StaticAbility implements AlternativeSourceCosts {

    protected static final String EVOKE_KEYWORD = "Evoke";
    protected static final String REMINDER_TEXT = "(You may cast this spell for its evoke cost. "
            + "If you do, it's sacrificed when it enters the battlefield.)";

    protected List<AlternativeCost2> evokeCosts = new LinkedList<>();

    // needed to check activation status, if card changes zone after casting it
    private int zoneChangeCounter = 0;

    public EvokeAbility(String manaString) {
        this(new ManaCostsImpl<>(manaString));
    }

    public EvokeAbility(Cost cost) {
        super(Zone.ALL, null);
        name = EVOKE_KEYWORD;
        this.addEvokeCost(cost);
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new SacrificeSourceEffect()),
                EvokedCondition.instance, "Sacrifice {this} when it enters the battlefield and was evoked.");
        ability.setRuleVisible(false);
        addSubAbility(ability);
    }

    private EvokeAbility(final EvokeAbility ability) {
        super(ability);
        this.evokeCosts.addAll(ability.evokeCosts);
        this.zoneChangeCounter = ability.zoneChangeCounter;
    }

    @Override
    public EvokeAbility copy() {
        return new EvokeAbility(this);
    }

    public final AlternativeCost2 addEvokeCost(Cost cost) {
        AlternativeCost2 evokeCost = new AlternativeCost2Impl<>(EVOKE_KEYWORD, REMINDER_TEXT, cost);
        evokeCosts.add(evokeCost);
        return evokeCost;
    }

    public void resetEvoke() {
        for (AlternativeCost2 cost : evokeCosts) {
            cost.reset();
        }
        zoneChangeCounter = 0;
    }

    @Override
    public boolean isActivated(Ability ability, Game game) {
        Card card = game.getCard(sourceId);
        if (card != null
                && card.getZoneChangeCounter(game) <= zoneChangeCounter + 1) {
            for (AlternativeCost2 cost : evokeCosts) {
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
                this.resetEvoke();
                for (AlternativeCost2 evokeCost : evokeCosts) {
                    if (evokeCost.canPay(ability, this, controllerId, game)
                            && player.chooseUse(Outcome.Benefit, new StringBuilder(EVOKE_KEYWORD).append(" the creature for ").append(evokeCost.getText(true)).append(" ?").toString(), ability, game)) {
                        activateEvoke(evokeCost, game);
                        ability.getManaCostsToPay().clear();
                        ability.getCosts().clear();
                        for (Iterator it = ((Costs) evokeCost).iterator(); it.hasNext();) {
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

    private void activateEvoke(AlternativeCost2 cost, Game game) {
        cost.activate();
        // remember zone change counter
        if (zoneChangeCounter == 0) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                zoneChangeCounter = card.getZoneChangeCounter(game);
            } else {
                throw new IllegalArgumentException("Evoke source card not found");
            }
        }
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        int numberCosts = 0;
        String remarkText = "";
        for (AlternativeCost2 evokeCost : evokeCosts) {
            if (numberCosts == 0) {
                sb.append(evokeCost.getText(false));
                remarkText = evokeCost.getReminderText();
            } else {
                sb.append(" and/or ").append(evokeCost.getText(true));
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
        for (AlternativeCost2 cost : evokeCosts) {
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
        for (AlternativeCost2 aCost : evokeCosts) {
            alterCosts.add(aCost.getCost());
        }
        return alterCosts;
    }
}
