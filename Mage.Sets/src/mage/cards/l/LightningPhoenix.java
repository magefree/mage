package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LightningPhoenix extends CardImpl {

    public LightningPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Lightning Phoenix can't block.
        this.addAbility(new CantBlockAbility());

        // At the beginning of your end step, if an opponent was dealt 3 or more damage this turn, you may pay {R}. If you do, return Lightning Phoenix from your graveyard to the battlefield.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        Zone.GRAVEYARD,
                        new DoIfCostPaid(
                                new ReturnSourceFromGraveyardToBattlefieldEffect(), new ManaCostsImpl<>("{R}")
                        ), TargetController.YOU, null, false
                ), LightningPhoenixCondition.instance, "At the beginning of your end step, " +
                "if an opponent was dealt 3 or more damage this turn, you may pay {R}. " +
                "If you do, return {this} from your graveyard to the battlefield."
        ), new LightningPhoenixWatcher());
    }

    private LightningPhoenix(final LightningPhoenix card) {
        super(card);
    }

    @Override
    public LightningPhoenix copy() {
        return new LightningPhoenix(this);
    }
}

enum LightningPhoenixCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        LightningPhoenixWatcher watcher = game.getState().getWatcher(LightningPhoenixWatcher.class);
        return watcher != null && watcher.checkDamage(source.getControllerId());
    }
}

class LightningPhoenixWatcher extends Watcher {

    private final Map<UUID, Integer> damageMap = new HashMap<>();

    LightningPhoenixWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER) {
            return;
        }
        for (UUID playerId : game.getOpponents(event.getTargetId())) {
            damageMap.compute(playerId, ((u, i) -> i == null ? event.getAmount() : Integer.sum(i, event.getAmount())));
        }
    }

    @Override
    public void reset() {
        super.reset();
        damageMap.clear();
    }

    boolean checkDamage(UUID playerId) {
        return damageMap.getOrDefault(playerId, 0) >= 3;
    }
}
