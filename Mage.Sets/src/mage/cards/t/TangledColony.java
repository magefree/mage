package mage.cards.t;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.RatCantBlockToken;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class TangledColony extends CardImpl {

    // Hint is there for two reasons:
    // -> Display the info for the trigger on the stack
    // -> If the permanent is regenerated, the current damage dealt is not the right info on the permanent
    private static final Hint hint =
            new ValueHint("Damage dealt to {this} this turn", TangledColonyDynamicValue.instance);

    public TangledColony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.RAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Tangled Colony can't block.
        this.addAbility(new CantBlockAbility());

        // When Tangled Colony dies, create X 1/1 black Rat creature tokens with "This creature can't block," where X is the amount of damage dealt to it this turn.
        Ability ability = new DiesSourceTriggeredAbility(
                new CreateTokenEffect(
                        new RatCantBlockToken(),
                        TangledColonyDynamicValue.instance
                ).setText("create X 1/1 black Rat creature tokens with \"This creature can't block,\" "
                        + "where X is the amount of damage dealt to it this turn.")
        );
        ability.addHint(hint);
        this.addAbility(ability, new TangledColonyWatcher());
    }

    private TangledColony(final TangledColony card) {
        super(card);
    }

    @Override
    public TangledColony copy() {
        return new TangledColony(this);
    }
}

class TangledColonyWatcher extends Watcher {

    // permanent's MOR -> amount of damage dealt to that permanent this turn
    private Map<MageObjectReference, Integer> damageMap = new HashMap<>();

    TangledColonyWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PERMANENT) {
            return;
        }
        Permanent damaged = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (damaged == null) {
            return;
        }
        MageObjectReference mor = new MageObjectReference(damaged, game);
        damageMap.compute(mor, (mor_, i) -> i == null ? event.getAmount() : Integer.sum(i, event.getAmount()));
    }

    @Override
    public void reset() {
        super.reset();
        damageMap.clear();
    }

    public Integer getDamage(MageObjectReference mor) {
        return damageMap.getOrDefault(mor, 0);
    }
}

enum TangledColonyDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        TangledColonyWatcher watcher = game.getState().getWatcher(TangledColonyWatcher.class);
        if (sourceAbility != null && watcher != null) {
            Permanent damaged = game.getPermanentOrLKIBattlefield(sourceAbility.getSourceId());
            if (damaged != null) {
                return watcher.getDamage(new MageObjectReference(damaged, game));
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "damage dealt to the source permanent";
    }

    @Override
    public String toString() {
        return "";
    }
}