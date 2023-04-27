package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

import java.util.Iterator;

/**
 * 702.25. Buyback
 * <p>
 * 702.25a Buyback appears on some instants and sorceries. It represents two
 * static abilities that function while the spell is on the stack. "Buyback
 * [cost]" means "You may pay an additional [cost] as you cast this spell" and
 * "If the buyback cost was paid, put this spell into its owner's hand instead
 * of into that player's graveyard as it resolves." Paying a spell's buyback
 * cost follows the rules for paying additional costs in rules 601.2b and
 * 601.2e-g.
 *
 * @author LevelX2
 */
public class BuybackAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private static final String keywordText = "Buyback";
    private static final String reminderTextCost = "You may {cost} in addition to any other costs as you cast this spell. If you do, put this card into your hand as it resolves.";
    private static final String reminderTextMana = "You may pay an additional {cost} as you cast this spell. If you do, put this card into your hand as it resolves.";

    protected OptionalAdditionalCost buybackCost;
    private int amountToReduceBy = 0;

    public BuybackAbility(String manaString) {
        super(Zone.STACK, new BuybackEffect());
        addBuybackCostAndSetup(new OptionalAdditionalCostImpl(keywordText, reminderTextMana, new ManaCostsImpl<>(manaString)));
        setRuleAtTheTop(true);
    }

    public BuybackAbility(Cost cost) {
        super(Zone.STACK, new BuybackEffect());
        addBuybackCostAndSetup(new OptionalAdditionalCostImpl(keywordText, "&mdash;", reminderTextCost, cost));
        setRuleAtTheTop(true);
    }

    private void addBuybackCostAndSetup(OptionalAdditionalCost newCost) {
        this.buybackCost = newCost;
        this.buybackCost.setCostType(VariableCostType.ADDITIONAL);
    }

    public BuybackAbility(final BuybackAbility ability) {
        super(ability);
        this.buybackCost = ability.buybackCost.copy();
        this.amountToReduceBy = ability.amountToReduceBy;
    }

    @Override
    public BuybackAbility copy() {
        return new BuybackAbility(this);
    }

    @Override
    public void addCost(Cost cost) {
        ((OptionalAdditionalCostImpl) buybackCost).add(cost);
    }

    public void resetReduceCost() {
        amountToReduceBy = 0;
    }

    // Called by Memory Crystal to reduce mana costs.
    public int reduceCost(int genericManaToReduce) {
        int amountToReduce = genericManaToReduce;
        boolean foundCostToReduce = false;
        for (Object cost : ((Costs) buybackCost)) {
            if (cost instanceof ManaCostsImpl) {
                for (Object c : (ManaCostsImpl) cost) {
                    if (c instanceof GenericManaCost) {
                        int newCostCMC = ((GenericManaCost) c).manaValue() - amountToReduceBy - genericManaToReduce;
                        foundCostToReduce = true;
                        if (newCostCMC > 0) {
                            amountToReduceBy += genericManaToReduce;
                        } else {
                            amountToReduce = ((GenericManaCost) c).manaValue() - amountToReduceBy;
                            amountToReduceBy = ((GenericManaCost) c).manaValue();
                        }
                    }
                }
            }
        }

        if (foundCostToReduce) {
            return amountToReduce;
        }
        return 0;
    }

    @Override
    public boolean isActivated() {
        return buybackCost.isActivated();
    }

    private void resetBuyback(Game game) {
        activateBuyback(game, false);
        resetReduceCost();
        buybackCost.reset();
    }

    private void activateBuyback(Game game, Boolean isActivated) {
        // xmage uses copies, all statuses must be saved to game state, not abilities
        game.getState().setValue(this.getSourceId().toString() + "_activatedBuyback", isActivated);

        // for extra info in cast message
        if (isActivated) {
            buybackCost.activate();
        } else {
            buybackCost.reset();
        }
    }

    public boolean isBuybackActivated(Game game) {
        return Boolean.TRUE.equals(game.getState().getValue(this.getSourceId().toString() + "_activatedBuyback"));
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            Player player = game.getPlayer(ability.getControllerId());
            if (player != null) {
                this.resetBuyback(game);
                // TODO: add AI support to find mana available to pay buyback
                //  canPay checks only single mana available, not total mana usage
                if (player.chooseUse(/*Outcome.Benefit*/ Outcome.AIDontUseIt, "Pay " + buybackCost.getText(false) + " ?", ability, game)) {
                    activateBuyback(game, true);
                    for (Iterator it = ((Costs) buybackCost).iterator(); it.hasNext(); ) {
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

    @Override
    public String getRule() {
        return buybackCost.getText(false) + ' ' + buybackCost.getReminderText();
    }

    @Override
    public String getCastMessageSuffix() {
        return buybackCost.getCastSuffixMessage(0);
    }
}

class BuybackEffect extends ReplacementEffectImpl {

    public BuybackEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "When {this} resolves and you payed buyback costs, put it back to hand instead";
    }

    public BuybackEffect(final BuybackEffect effect) {
        super(effect);
    }

    @Override
    public BuybackEffect copy() {
        return new BuybackEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            // if spell fizzled, the sourceId is null
            return zEvent.getFromZone() == Zone.STACK && zEvent.getToZone() == Zone.GRAVEYARD
                    && source.getSourceId().equals(event.getSourceId());
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(source.getSourceId());
        if (card != null && source instanceof BuybackAbility) {
            if (((BuybackAbility) source).isBuybackActivated(game)) {
                return card.moveToZone(Zone.HAND, source, game, true, event.getAppliedEffects());
            }
        }
        return false;
    }

}
