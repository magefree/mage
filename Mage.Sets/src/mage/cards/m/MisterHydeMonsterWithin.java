package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author miesma
 */
public final class MisterHydeMonsterWithin extends CardImpl {

    public MisterHydeMonsterWithin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, choose one
        // Put a +1/+1 counter on Mister Hyde.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));

        // Remove a counter from a creature you control. If you do, draw a card.
        Mode mode = new Mode(new MisterHydeMonsterWithinRemoveCounterEffect());
        ability.addMode(mode);

        this.addAbility(ability);
    }

    private MisterHydeMonsterWithin(final MisterHydeMonsterWithin card) {
        super(card);
    }

    @Override
    public MisterHydeMonsterWithin copy() {
        return new MisterHydeMonsterWithin(this);
    }
}

class MisterHydeMonsterWithinRemoveCounterEffect extends OneShotEffect {

    MisterHydeMonsterWithinRemoveCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Remove a counter from a creature you control. If you do, draw a card";
    }

    private MisterHydeMonsterWithinRemoveCounterEffect(final MisterHydeMonsterWithinRemoveCounterEffect effect) {
        super(effect);
    }

    @Override
    public MisterHydeMonsterWithinRemoveCounterEffect copy() {
        return new MisterHydeMonsterWithinRemoveCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cost cost = new RemoveCounterCost(new TargetControlledCreaturePermanent().withNotTarget(true));
        if (cost.canPay(source, source, source.getControllerId(), game)) {
            if (cost.pay(source, game, source, source.getControllerId(), true)) {
                player.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }
}

