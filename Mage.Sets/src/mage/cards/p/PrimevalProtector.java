package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PrimevalProtector extends CardImpl {

    public PrimevalProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}{G}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Primeval Protector costs {1} less to cast for each creature your opponents control.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new PrimevalProtectorCostReductionEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // When Primeval Protector enters the battlefield, put +1/+1 counter on each other creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE), false));
    }

    private PrimevalProtector(final PrimevalProtector card) {
        super(card);
    }

    @Override
    public PrimevalProtector copy() {
        return new PrimevalProtector(this);
    }
}

class PrimevalProtectorCostReductionEffect extends CostModificationEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each creature your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    PrimevalProtectorCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "this spell costs {1} less to cast for each creature your opponents control";
    }

    private PrimevalProtectorCostReductionEffect(final PrimevalProtectorCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int reductionAmount = game.getBattlefield().count(filter, source.getControllerId(), source, game);
            CardUtil.reduceCost(abilityToModify, reductionAmount);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility) && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public PrimevalProtectorCostReductionEffect copy() {
        return new PrimevalProtectorCostReductionEffect(this);
    }
}
