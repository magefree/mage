package mage.cards.m;

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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author emerald000 & L_J
 */
public final class MoggToady extends CardImpl {

    public MoggToady(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Mogg Toady can't attack unless you control more creatures than defending player.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MoggToadyCantAttackEffect()));

        // Mogg Toady can't block unless you control more creatures than attacking player.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MoggToadyCantBlockEffect()));
    }

    private MoggToady(final MoggToady card) {
        super(card);
    }

    @Override
    public MoggToady copy() {
        return new MoggToady(this);
    }
}

class MoggToadyCantAttackEffect extends RestrictionEffect {

    MoggToadyCantAttackEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you control more creatures than defending player";
    }

    private MoggToadyCantAttackEffect(final MoggToadyCantAttackEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }

        UUID defendingPlayerId;
        Player defender = game.getPlayer(defenderId);
        if (defender == null) {
            Permanent permanent = game.getPermanent(defenderId);
            if (permanent != null) {
                defendingPlayerId = permanent.getControllerId();
            } else {
                return false;
            }
        } else {
            defendingPlayerId = defenderId;
        }
        if (defendingPlayerId != null) {
            return game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), source.getControllerId(), game) > game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), defendingPlayerId, game);
        } else {
            return true;
        }
    }

    @Override
    public MoggToadyCantAttackEffect copy() {
        return new MoggToadyCantAttackEffect(this);
    }
}

class MoggToadyCantBlockEffect extends RestrictionEffect {

    MoggToadyCantBlockEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't block unless you control more creatures than attacking player";
    }

    private MoggToadyCantBlockEffect(final MoggToadyCantBlockEffect effect) {
        super(effect);
    }

    @Override
    public MoggToadyCantBlockEffect copy() {
        return new MoggToadyCantBlockEffect(this);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker == null) {
            return true;
        }
        UUID attackingPlayerId = attacker.getControllerId();
        if (attackingPlayerId != null) {
            return game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), source.getControllerId(), game) > game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), attackingPlayerId, game);
        }
        return true;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }
}
