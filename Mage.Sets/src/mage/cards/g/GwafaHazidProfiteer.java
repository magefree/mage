package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
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
        Ability ability = new SimpleActivatedAbility(new GwafaHazidProfiteerEffect1(), new ManaCostsImpl<>("{W}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);

        // Creatures with bribery counters on them can't attack or block.
        this.addAbility(new SimpleStaticAbility(new GwafaHazidProfiteerEffect2()));
    }

    private GwafaHazidProfiteer(final GwafaHazidProfiteer card) {
        super(card);
    }

    @Override
    public GwafaHazidProfiteer copy() {
        return new GwafaHazidProfiteer(this);
    }
}

class GwafaHazidProfiteerEffect1 extends OneShotEffect {

    GwafaHazidProfiteerEffect1() {
        super(Outcome.Detriment);
        staticText = "Put a bribery counter on target creature you don't control. Its controller draws a card";
    }

    private GwafaHazidProfiteerEffect1(final GwafaHazidProfiteerEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            Player controller = game.getPlayer(targetCreature.getControllerId());
            targetCreature.addCounters(CounterType.BRIBERY.createInstance(), source.getControllerId(), source, game);
            if (controller != null) {
                controller.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public GwafaHazidProfiteerEffect1 copy() {
        return new GwafaHazidProfiteerEffect1(this);
    }


}

class GwafaHazidProfiteerEffect2 extends RestrictionEffect {

    GwafaHazidProfiteerEffect2() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures with bribery counters on them can't attack or block";
    }

    private GwafaHazidProfiteerEffect2(final GwafaHazidProfiteerEffect2 effect) {
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
    public GwafaHazidProfiteerEffect2 copy() {
        return new GwafaHazidProfiteerEffect2(this);
    }
}
