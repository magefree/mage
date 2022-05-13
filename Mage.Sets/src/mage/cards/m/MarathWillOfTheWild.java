
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManaSpentToCastCount;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MarathWillOfTheWildElementalToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MarathWillOfTheWild extends CardImpl {

    public MarathWillOfTheWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Marath, Will of the Wild enters the battlefield with a number of +1/+1 counters on it equal to the amount of mana spent to cast it.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), ManaSpentToCastCount.instance, true);
        effect.setText("with a number of +1/+1 counters on it equal to the amount of mana spent to cast it");
        this.addAbility(new EntersBattlefieldAbility(effect));

        // {X}, Remove X +1/+1 counters from Marath: Choose one - Put X +1/+1 counters on target creature;
        effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(0), ManacostVariableValue.REGULAR);
        effect.setText("Put X +1/+1 counters on target creature");
        Ability ability = new SimpleActivatedAbility(effect, new ManaCostsImpl("{X}"));
        ability.addCost(new MarathWillOfTheWildRemoveCountersCost());
        ability.addTarget(new TargetCreaturePermanent());

        // or Marath deals X damage to any target;
        Mode mode = new Mode(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        mode.addTarget(new TargetAnyTarget());
        ability.addMode(mode);

        // or create an X/X green Elemental creature token.
        mode = new Mode(new MarathWillOfTheWildCreateTokenEffect());
        ability.addMode(mode);

        // X can't be 0.
        for (VariableCost cost : ability.getManaCosts().getVariableCosts()) {
            if (cost instanceof VariableManaCost) {
                ((VariableManaCost) cost).setMinX(1);
                break;
            }
        }
        this.addAbility(ability);
    }

    private MarathWillOfTheWild(final MarathWillOfTheWild card) {
        super(card);
    }

    @Override
    public MarathWillOfTheWild copy() {
        return new MarathWillOfTheWild(this);
    }
}

class MarathWillOfTheWildCreateTokenEffect extends OneShotEffect {

    MarathWillOfTheWildCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create an X/X green Elemental creature token";
    }

    private MarathWillOfTheWildCreateTokenEffect(final MarathWillOfTheWildCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public MarathWillOfTheWildCreateTokenEffect copy() {
        return new MarathWillOfTheWildCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int amount = ManacostVariableValue.REGULAR.calculate(game, source, this);
            Token token = new MarathWillOfTheWildElementalToken();
            token.getPower().modifyBaseValue(amount);
            token.getToughness().modifyBaseValue(amount);
            token.putOntoBattlefield(1, game, source, source.getControllerId());
            return true;
        }
        return false;
    }
}

class MarathWillOfTheWildRemoveCountersCost extends CostImpl {

    MarathWillOfTheWildRemoveCountersCost() {
        this.text = "Remove X +1/+1 counters from Marath";

    }

    private MarathWillOfTheWildRemoveCountersCost(MarathWillOfTheWildRemoveCountersCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        int amount = ManacostVariableValue.REGULAR.calculate(game, ability, null);
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getCounters(game).getCount(CounterType.P1P1) >= amount) {
            permanent.removeCounters(CounterType.P1P1.getName(), amount, source, game);
            this.paid = true;
        }
        return paid;
    }

    @Override
    public MarathWillOfTheWildRemoveCountersCost copy() {
        return new MarathWillOfTheWildRemoveCountersCost(this);
    }
}
