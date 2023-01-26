package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class SilentArbiter extends CardImpl {

    public SilentArbiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // No more than one creature can attack each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SilentArbiterAttackRestrictionEffect()));

        // No more than one creature can block each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SilentArbiterBlockRestrictionEffect()));
    }

    private SilentArbiter(final SilentArbiter card) {
        super(card);
    }

    @Override
    public SilentArbiter copy() {
        return new SilentArbiter(this);
    }
}

class SilentArbiterAttackRestrictionEffect extends RestrictionEffect {

    SilentArbiterAttackRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "No more than one creature can attack each combat";
    }

    SilentArbiterAttackRestrictionEffect(final SilentArbiterAttackRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public SilentArbiterAttackRestrictionEffect copy() {
        return new SilentArbiterAttackRestrictionEffect(this);
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

class SilentArbiterBlockRestrictionEffect extends RestrictionEffect {

    SilentArbiterBlockRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "No more than one creature can block each combat";
    }

    SilentArbiterBlockRestrictionEffect(final SilentArbiterBlockRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public SilentArbiterBlockRestrictionEffect copy() {
        return new SilentArbiterBlockRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent newBlocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker == null) {
            return true;
        }
        for (UUID creatureId : game.getCombat().getBlockers()) {
            Permanent existingBlocker = game.getPermanent(creatureId);
            if (game.getPlayer(existingBlocker.getControllerId()).hasOpponent(attacker.getControllerId(), game) && existingBlocker.isControlledBy(newBlocker.getControllerId())) {
                return false;
            }
        }
        return true;
    }
}