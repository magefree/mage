package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.SacrificeEquippedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author Quercitron
 */
public final class ThirstingAxe extends CardImpl {

    public ThirstingAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +4/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(4, 0)));

        // At the beginning of your end step, if equipped creature didn't deal combat damage to a creature this turn, sacrifice it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new SacrificeEquippedEffect().setText("sacrifice it")
        ).withInterveningIf(ThirstingAxeCondition.instance), new ThirstingAxeWatcher());

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private ThirstingAxe(final ThirstingAxe card) {
        super(card);
    }

    @Override
    public ThirstingAxe copy() {
        return new ThirstingAxe(this);
    }
}

enum ThirstingAxeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .filter(uuid -> ThirstingAxeWatcher.checkCreature(uuid, game))
                .isPresent();
    }

    @Override
    public String toString() {
        return "equipped creature didn't deal combat damage to a creature this turn";
    }
}

class ThirstingAxeWatcher extends Watcher {

    // which objects dealt combat damage to creature during the turn
    private final Set<MageObjectReference> dealtCombatDamageToCreature = new HashSet<>();

    public ThirstingAxeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PERMANENT || !((DamagedEvent) event).isCombatDamage()) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isCreature(game)) {
            dealtCombatDamageToCreature.add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        dealtCombatDamageToCreature.clear();
    }

    static boolean checkCreature(UUID permanentId, Game game) {
        return game
                .getState()
                .getWatcher(ThirstingAxeWatcher.class)
                .dealtCombatDamageToCreature
                .stream()
                .anyMatch(mor -> mor.refersTo(permanentId, game));
    }
}
