package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author L_J
 */
public final class PrimordialOoze extends CardImpl {

    public PrimordialOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Primordial Ooze attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
        // At the beginning of your upkeep, put a +1/+1 counter on Primordial Ooze. Then you may pay {X}, where X is the number of +1/+1 counters on it. If you don't, tap Primordial Ooze and it deals X damage to you.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), TargetController.YOU, false);
        ability.addEffect(new PrimordialOozeEffect());
        this.addAbility(ability);
    }

    private PrimordialOoze(final PrimordialOoze card) {
        super(card);
    }

    @Override
    public PrimordialOoze copy() {
        return new PrimordialOoze(this);
    }
}

class PrimordialOozeEffect extends OneShotEffect {

    public PrimordialOozeEffect() {
        super(Outcome.Detriment);
        this.staticText = "Then you may pay {X}, where X is the number of +1/+1 counters on it. If you don't, tap {this} and it deals X damage to you";
    }

    public PrimordialOozeEffect(final PrimordialOozeEffect effect) {
        super(effect);
    }

    @Override
    public PrimordialOozeEffect copy() {
        return new PrimordialOozeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = source.getSourcePermanentIfItStillExists(game);
        if (controller != null && sourceObject != null) {
            int counter = sourceObject.getCounters(game).getCount(CounterType.P1P1);
            Cost cost = new ManaCostsImpl<>("{" + counter + '}');
            if (!(controller.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + " to prevent taking " + counter + " damage from " + sourceObject.getLogName() + "?", source, game)
                    && cost.pay(source, game, source, controller.getId(), false, null))) {
                sourceObject.tap(source, game);
                controller.damage(counter, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }
}
