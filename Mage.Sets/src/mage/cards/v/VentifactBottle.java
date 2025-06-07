package mage.cards.v;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VentifactBottle extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.CHARGE);

    public VentifactBottle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {X}{1}, {tap}: Put X charge counters on Ventifact Bottle. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new AddCountersSourceEffect(
                CounterType.CHARGE.createInstance(), GetXValue.instance, true
        ), new ManaCostsImpl<>("{X}{1}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // At the beginning of your precombat main phase, if Ventifact Bottle has a charge counter on it, tap it and remove all charge counters from it. Add {C} for each charge counter removed this way.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(new VentifactBottleEffect()).withInterveningIf(condition));
    }

    private VentifactBottle(final VentifactBottle card) {
        super(card);
    }

    @Override
    public VentifactBottle copy() {
        return new VentifactBottle(this);
    }
}

class VentifactBottleEffect extends OneShotEffect {

    VentifactBottleEffect() {
        super(Outcome.Benefit);
        staticText = "tap it and remove all charge counters from it. Add {C} for each charge counter removed this way";
    }

    private VentifactBottleEffect(final VentifactBottleEffect effect) {
        super(effect);
    }

    @Override
    public VentifactBottleEffect copy() {
        return new VentifactBottleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || sourcePermanent == null) {
            return false;
        }
        sourcePermanent.tap(source, game);
        int amountRemoved = sourcePermanent.removeAllCounters(CounterType.CHARGE.getName(), source, game);
        if (amountRemoved > 0) {
            player.getManaPool().addMana(Mana.ColorlessMana(amountRemoved), game, source);
        }
        return true;
    }
}
