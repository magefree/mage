
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class CoalitionRelic extends CardImpl {

    public CoalitionRelic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {tap}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
        // {tap}: Put a charge counter on Coalition Relic.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(), true), new TapSourceCost()));
        // At the beginning of your precombat main phase, remove all charge counters from Coalition Relic. Add one mana of any color for each charge counter removed this way.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(new CoalitionRelicEffect(), TargetController.YOU, false));
    }

    private CoalitionRelic(final CoalitionRelic card) {
        super(card);
    }

    @Override
    public CoalitionRelic copy() {
        return new CoalitionRelic(this);
    }
}

class CoalitionRelicEffect extends OneShotEffect {

    public CoalitionRelicEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "remove all charge counters from Coalition Relic. Add one mana of any color for each charge counter removed this way";
    }

    private CoalitionRelicEffect(final CoalitionRelicEffect effect) {
        super(effect);
    }

    @Override
    public CoalitionRelicEffect copy() {
        return new CoalitionRelicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (sourcePermanent != null && player != null) {
            int chargeCounters = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE);
            sourcePermanent.removeCounters(CounterType.CHARGE.createInstance(chargeCounters), source, game);
            Mana mana = new Mana();
            ChoiceColor choice = new ChoiceColor();
            for (int i = 0; i < chargeCounters; i++) {
                if (!player.choose(outcome, choice, game)) {
                    return false;
                }
                choice.increaseMana(mana);
                choice.clearChoice();
            }
            player.getManaPool().addMana(mana, game, source);
            return true;
        }
        return false;
    }
}
