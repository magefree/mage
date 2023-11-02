package mage.cards.g;

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
 * @author emerald000
 */
public final class GoblinGoon extends CardImpl {

    public GoblinGoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Goblin Goon can't attack unless you control more creatures than defending player.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GoblinGoonCantAttackEffect()));

        // Goblin Goon can't block unless you control more creatures than attacking player.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GoblinGoonCantBlockEffect()));
    }

    private GoblinGoon(final GoblinGoon card) {
        super(card);
    }

    @Override
    public GoblinGoon copy() {
        return new GoblinGoon(this);
    }
}

class GoblinGoonCantAttackEffect extends RestrictionEffect {

    GoblinGoonCantAttackEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you control more creatures than defending player";
    }

    private GoblinGoonCantAttackEffect(final GoblinGoonCantAttackEffect effect) {
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
    public GoblinGoonCantAttackEffect copy() {
        return new GoblinGoonCantAttackEffect(this);
    }
}

class GoblinGoonCantBlockEffect extends RestrictionEffect {

    GoblinGoonCantBlockEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't block unless you control more creatures than attacking player";
    }

    private GoblinGoonCantBlockEffect(final GoblinGoonCantBlockEffect effect) {
        super(effect);
    }

    @Override
    public GoblinGoonCantBlockEffect copy() {
        return new GoblinGoonCantBlockEffect(this);
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
