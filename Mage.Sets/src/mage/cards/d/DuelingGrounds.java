package mage.cards.d;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;
import mage.watchers.common.BlockedThisTurnWatcher;

import java.util.Set;
import java.util.UUID;

/**
 * @author Quercitron
 */
public final class DuelingGrounds extends CardImpl {

    public DuelingGrounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{W}");

        // No more than one creature can attack each turn.
        this.addAbility(
                new SimpleStaticAbility(Zone.BATTLEFIELD, new NoMoreThanOneCreatureCanAttackEachTurnEffect()),
                new AttackedThisTurnWatcher());

        // No more than one creature can block each turn.
        this.addAbility(
                new SimpleStaticAbility(Zone.BATTLEFIELD, new NoMoreThanOneCreatureCanBlockEachTurnEffect()),
                new BlockedThisTurnWatcher());
    }

    private DuelingGrounds(final DuelingGrounds card) {
        super(card);
    }

    @Override
    public DuelingGrounds copy() {
        return new DuelingGrounds(this);
    }
}

class NoMoreThanOneCreatureCanAttackEachTurnEffect extends RestrictionEffect {

    public NoMoreThanOneCreatureCanAttackEachTurnEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "No more than one creature can attack each turn";
    }

    public NoMoreThanOneCreatureCanAttackEachTurnEffect(final NoMoreThanOneCreatureCanAttackEachTurnEffect effect) {
        super(effect);
    }

    @Override
    public ContinuousEffect copy() {
        return new NoMoreThanOneCreatureCanAttackEachTurnEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (!game.getCombat().getAttackers().isEmpty()) {
            return false;
        }
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        if (watcher == null) {
            return false;
        }
        Set<MageObjectReference> attackedThisTurnCreatures = watcher.getAttackedThisTurnCreatures();
        return attackedThisTurnCreatures.isEmpty()
                || (attackedThisTurnCreatures.size() == 1 && attackedThisTurnCreatures.contains(new MageObjectReference(attacker, game)));
    }

}

class NoMoreThanOneCreatureCanBlockEachTurnEffect extends RestrictionEffect {

    public NoMoreThanOneCreatureCanBlockEachTurnEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "No more than one creature can block each turn";
    }

    public NoMoreThanOneCreatureCanBlockEachTurnEffect(final NoMoreThanOneCreatureCanBlockEachTurnEffect effect) {
        super(effect);
    }

    @Override
    public ContinuousEffect copy() {
        return new NoMoreThanOneCreatureCanBlockEachTurnEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (!game.getCombat().getBlockers().isEmpty()) {
            return false;
        }
        BlockedThisTurnWatcher watcher = game.getState().getWatcher(BlockedThisTurnWatcher.class);
        if (watcher == null) {
            return false;
        }
        Set<MageObjectReference> blockedThisTurnCreatures = watcher.getBlockedThisTurnCreatures();
        MageObjectReference blockerReference = new MageObjectReference(blocker.getId(), blocker.getZoneChangeCounter(game), game);
        return blockedThisTurnCreatures.isEmpty()
                || (blockedThisTurnCreatures.size() == 1 && blockedThisTurnCreatures.contains(blockerReference));
    }

}
