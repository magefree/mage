package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author Momo2907
 */
public final class RottenmouthViper extends CardImpl {

    public RottenmouthViper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");
        
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // As an additional cost to cast this spell, you may sacrifice any number of nonland permanents. This spell costs {1} less to cast for each permanent sacrificed this way.
        Cost cost = new SacrificeXTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND, true);
        cost.setText("you may sacrifice any number of nonland permanents. " +
                "This spell costs {1} less to cast for each permanent sacrificed this way");
        this.getSpellAbility().addCost(cost);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new RottenmouthViperCostReductionEffect()));

        // Whenever Rottenmouth Viper enters or attacks, put a blight counter on it. Then for each blight counter on it, each opponent loses 4 life unless that player sacrifices a nonland permanent or discards a card.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new AddCountersSourceEffect(CounterType.BLIGHT.createInstance())
        ).withRuleTextReplacement(true);
        ability.addEffect(new RottenmouthViperEffect(new CountersSourceCount(CounterType.BLIGHT)));
        this.addAbility(ability);
    }

    private RottenmouthViper(final RottenmouthViper card) {
        super(card);
    }

    @Override
    public RottenmouthViper copy() {
        return new RottenmouthViper(this);
    }
}

class RottenmouthViperEffect extends OneShotEffect{

    DynamicValue blightCounterAmount;

    RottenmouthViperEffect(DynamicValue blightCounterAmount) {
        super(Outcome.LoseLife);
        this.blightCounterAmount = blightCounterAmount;
        this.staticText = "Then for each blight counter on it, each opponent loses 4 life unless that player sacrifices a nonland permanent of their choice or discards a card.";
    }

    private RottenmouthViperEffect(final RottenmouthViperEffect effect) {
        super(effect);
        this.blightCounterAmount = effect.blightCounterAmount.copy();
    }

    @Override
    public RottenmouthViperEffect copy() {
        return new RottenmouthViperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null){
            int repeat = blightCounterAmount.calculate(game, source, this);
            for (int i = 1; i <= repeat; i++) {

                for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                    Player opponent = game.getPlayer(opponentId);
                    if (opponent != null) {
                        // AI hint - use life by priority (ignore sacrifice and discard)
                        Outcome aiOutcome = opponent.getLife() > 10 ? Outcome.Detriment : Outcome.Benefit;
                        int permanents = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_NON_LAND, opponentId, game);
                        if (permanents > 0 && opponent.chooseUse(aiOutcome, "Sacrifices a nonland permanent? (Iteration " + i + " of " + repeat + ")",
                                "Otherwise you have to discard a card or lose 4 life.", "Sacrifice", "Discard or life loss", source, game)) {
                            Target target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND);
                            target.withNotTarget(true);
                            if (opponent.choose(Outcome.Sacrifice, target, source, game)) {
                                Permanent permanent = game.getPermanent(target.getFirstTarget());
                                if (permanent != null && permanent.sacrifice(source, game)) {
                                    continue;
                                }
                            }
                        }
                        if (!opponent.getHand().isEmpty() && opponent.chooseUse(aiOutcome, "Discard a card? (Iteration " + i + " of " + repeat + ")",
                                "Otherwise you lose 4 life.", "Discard", "Lose 4 life", source, game)) {
                            opponent.discardOne(false, false, source, game);
                            continue;
                        }
                        opponent.loseLife(4, game, source, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class RottenmouthViperCostReductionEffect extends CostModificationEffectImpl{

    RottenmouthViperCostReductionEffect(){
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
    }

    private RottenmouthViperCostReductionEffect(final RottenmouthViperCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public RottenmouthViperCostReductionEffect copy() {
        return new RottenmouthViperCostReductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        int reduction = 0;
        for (Cost cost: spellAbility.getCosts()) {
            if(!(cost instanceof SacrificeXTargetCost)){
                continue;
            }
            if (game.inCheckPlayableState()) {
                reduction += ((SacrificeXTargetCost) cost).getMaxValue(spellAbility, game);
            } else{
                reduction += ((SacrificeXTargetCost) cost).getAmount();
            }
            break;
        }
        CardUtil.adjustCost(spellAbility, reduction);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility && abilityToModify.getSourceId().equals(source.getSourceId());
    }
}
