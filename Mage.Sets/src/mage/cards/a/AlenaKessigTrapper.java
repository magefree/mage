package mage.cards.a;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.constants.*;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

/**
 * @author TheElk801
 */
public final class AlenaKessigTrapper extends CardImpl {

    public AlenaKessigTrapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {T}: Add an amount of {R} equal to the greatest power among creatures you control that entered the battlefield this turn.
        this.addAbility(new DynamicManaAbility(
                Mana.RedMana(1), AlenaKessigTrapperValue.instance, new TapSourceCost(), "Add an amount of {R} " +
                "equal to the greatest power among creatures you control that entered the battlefield this turn."
        ), new AlenaKessigTrapperWatcher());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private AlenaKessigTrapper(final AlenaKessigTrapper card) {
        super(card);
    }

    @Override
    public AlenaKessigTrapper copy() {
        return new AlenaKessigTrapper(this);
    }
}

enum AlenaKessigTrapperValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        AlenaKessigTrapperWatcher watcher = game.getState().getWatcher(AlenaKessigTrapperWatcher.class);
        if (watcher == null) {
            return 0;
        }
        return watcher.getPower(sourceAbility.getControllerId(), game);
    }

    @Override
    public AlenaKessigTrapperValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class AlenaKessigTrapperWatcher extends Watcher {

    private final Set<MageObjectReference> enteredThisTurn = new HashSet<>();

    public AlenaKessigTrapperWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getToZone() == Zone.BATTLEFIELD) {
            enteredThisTurn.add(new MageObjectReference(event.getTargetId(), game));
        }
    }

    @Override
    public void reset() {
        enteredThisTurn.clear();
        super.reset();
    }

    int getPower(UUID playerId, Game game) {
        return enteredThisTurn
                .stream()
                .filter(Objects::nonNull)
                .map(mor -> mor.getPermanent(game))
                .filter(Objects::nonNull)
                .filter(permanent1 -> permanent1.isCreature(game))
                .filter(permanent -> permanent.isControlledBy(playerId))
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
    }
}
