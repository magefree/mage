package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class DuelingGrounds extends CardImpl {

    public DuelingGrounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{W}");

        // No more than one creature can attack each turn.
        this.addAbility(new SimpleStaticAbility(new NoMoreThanOneCreatureCanAttackEachTurnEffect()));

        // No more than one creature can block each turn.
        this.addAbility(new SimpleStaticAbility(new NoMoreThanOneCreatureCanBlockEachTurnEffect()));
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

    NoMoreThanOneCreatureCanAttackEachTurnEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "No more than one creature can attack each combat";
    }

    private NoMoreThanOneCreatureCanAttackEachTurnEffect(final NoMoreThanOneCreatureCanAttackEachTurnEffect effect) {
        super(effect);
    }

    @Override
    public NoMoreThanOneCreatureCanAttackEachTurnEffect copy() {
        return new NoMoreThanOneCreatureCanAttackEachTurnEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        return game.getCombat().getAttackers().isEmpty();
    }

}

class NoMoreThanOneCreatureCanBlockEachTurnEffect extends RestrictionEffect {

    NoMoreThanOneCreatureCanBlockEachTurnEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "No more than one creature can block each combat";
    }

    private NoMoreThanOneCreatureCanBlockEachTurnEffect(final NoMoreThanOneCreatureCanBlockEachTurnEffect effect) {
        super(effect);
    }

    @Override
    public NoMoreThanOneCreatureCanBlockEachTurnEffect copy() {
        return new NoMoreThanOneCreatureCanBlockEachTurnEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker == null) {
            return true;
        }
        for (UUID creatureId : game.getCombat().getBlockers()) {
            Permanent existingBlocker = game.getPermanent(creatureId);
            if (game.getPlayer(existingBlocker.getControllerId()).hasOpponent(attacker.getControllerId(), game) && existingBlocker.isControlledBy(blocker.getControllerId())) {
                return false;
            }
        }
        return true;
    }

}
