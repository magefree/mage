package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearlessSwashbuckler extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.VEHICLE, "Vehicles");

    public FearlessSwashbuckler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Vehicles you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Whenever you attack, if a Pirate and a Vehicle attacked this combat, draw three cards, then discard two cards.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DrawDiscardControllerEffect(3, 2), 1
        ).withInterveningIf(FearlessSwashbucklerCondition.instance), new FearlessSwashbucklerWatcher());
    }

    private FearlessSwashbuckler(final FearlessSwashbuckler card) {
        super(card);
    }

    @Override
    public FearlessSwashbuckler copy() {
        return new FearlessSwashbuckler(this);
    }
}

enum FearlessSwashbucklerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getState().getWatcher(FearlessSwashbucklerWatcher.class).check();
    }

    @Override
    public String toString() {
        return "if a Pirate and a Vehicle attacked this combat";
    }
}

class FearlessSwashbucklerWatcher extends Watcher {

    private boolean pirateAttacked = false;
    private boolean vehicleAttacked = false;

    FearlessSwashbucklerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case ATTACKER_DECLARED:
                break;
            case COMBAT_PHASE_POST:
                pirateAttacked = false;
                vehicleAttacked = false;
            default:
                return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent == null) {
            return;
        }
        if (permanent.hasSubtype(SubType.PIRATE, game)) {
            pirateAttacked = true;
        }
        if (permanent.hasSubtype(SubType.VEHICLE, game)) {
            vehicleAttacked = true;
        }
    }

    @Override
    public void reset() {
        super.reset();
        pirateAttacked = false;
        vehicleAttacked = false;
    }

    boolean check() {
        return pirateAttacked && vehicleAttacked;
    }
}
