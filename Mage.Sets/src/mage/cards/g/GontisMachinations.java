package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.LoseLifeTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.LifeLostThisTurnWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GontisMachinations extends CardImpl {

    public GontisMachinations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // Whenever you lose life for the first time each turn, you get {E}.
        this.addAbility(new GontisMachinationsTriggeredAbility());

        // Pay {E}{E}, Sacrifice Gonti's Machinations: Each opponent loses 3 life. You gain life equal to the life lost this way.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new LoseLifeOpponentsYouGainLifeLostEffect(3),
                new PayEnergyCost(2));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private GontisMachinations(final GontisMachinations card) {
        super(card);
    }

    @Override
    public GontisMachinations copy() {
        return new GontisMachinations(this);
    }
}

class GontisMachinationsTriggeredAbility extends LoseLifeTriggeredAbility {

    public GontisMachinationsTriggeredAbility() {
        super(new GetEnergyCountersControllerEffect(1), TargetController.YOU);
        setTriggerPhrase("Whenever you lose life for the first time each turn, ");
        addWatcher(new LifeLostThisTurnWatcher());
    }

    private GontisMachinationsTriggeredAbility(final GontisMachinationsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GontisMachinationsTriggeredAbility copy() {
        return new GontisMachinationsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        LifeLostThisTurnWatcher watcher = game.getState().getWatcher(LifeLostThisTurnWatcher.class);
        return watcher != null
                && watcher.timesLostLifeThisTurn(event.getPlayerId()) <= 1
                && super.checkTrigger(event, game);
    }
}
