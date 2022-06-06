package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class HatchetBully extends CardImpl {

    public HatchetBully(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{R}, {tap}, Put a -1/-1 counter on a creature you control: Hatchet Bully deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HatchetBullyEffect(), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new HatchetBullyCost());
        ability.addTarget(new TargetAnyTarget());
        Target target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        ability.addTarget(target);
        this.addAbility(ability);

    }

    private HatchetBully(final HatchetBully card) {
        super(card);
    }

    @Override
    public HatchetBully copy() {
        return new HatchetBully(this);
    }
}

class HatchetBullyCost extends CostImpl {

    public HatchetBullyCost() {
        this.text = "Put a -1/-1 counter on a creature you control";
    }

    public HatchetBullyCost(HatchetBullyCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(ability.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.addCounters(CounterType.M1M1.createInstance(), controllerId, ability, game);
            this.paid = true;
        }
        return paid;
    }

    @Override
    public HatchetBullyCost copy() {
        return new HatchetBullyCost(this);
    }
}

class HatchetBullyEffect extends OneShotEffect {

    public HatchetBullyEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 2 damage to any target";
    }

    public HatchetBullyEffect(final HatchetBullyEffect effect) {
        super(effect);
    }

    @Override
    public HatchetBullyEffect copy() {
        return new HatchetBullyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage(2, source.getSourceId(), source, game, false, true);
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage(2, source.getSourceId(), source, game);
        }
        return true;
    }
}
