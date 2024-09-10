package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DrawCardTargetControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GwafaHazidProfiteer extends CardImpl {

    public GwafaHazidProfiteer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}{U}, {tap}: Put a bribery counter on target creature you don't control. Its controller draws a card.
        Ability ability = new SimpleActivatedAbility(new AddCountersTargetEffect(CounterType.BRIBERY.createInstance()), new ManaCostsImpl<>("{W}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        ability.addEffect(new DrawCardTargetControllerEffect(1));
        this.addAbility(ability);

        // Creatures with bribery counters on them can't attack or block.
        this.addAbility(new SimpleStaticAbility(new GwafaHazidProfiteerEffect()));
    }

    private GwafaHazidProfiteer(final GwafaHazidProfiteer card) {
        super(card);
    }

    @Override
    public GwafaHazidProfiteer copy() {
        return new GwafaHazidProfiteer(this);
    }
}

class GwafaHazidProfiteerEffect extends RestrictionEffect {

    GwafaHazidProfiteerEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures with bribery counters on them can't attack or block";
    }

    private GwafaHazidProfiteerEffect(final GwafaHazidProfiteerEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getCounters(game).containsKey(CounterType.BRIBERY);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public GwafaHazidProfiteerEffect copy() {
        return new GwafaHazidProfiteerEffect(this);
    }
}
