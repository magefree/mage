package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MonstrousHound extends CardImpl {

    public MonstrousHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Monstrous Hound can't attack unless you control more lands than defending player.
        Effect effect = new CantAttackUnlessControllerControlsMoreLandsEffect();
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                effect.setText("{this} can't attack unless you control more lands than defending player")));

        // Monstrous Hound can't block unless you control more lands than attacking player.
        Effect effect2 = new CantBlockUnlessControllerControlsMoreLandsEffect();
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                effect2.setText("{this} can't block unless you control more lands than attacking player")));

    }

    private MonstrousHound(final MonstrousHound card) {
        super(card);
    }

    @Override
    public MonstrousHound copy() {
        return new MonstrousHound(this);
    }
}

class CantAttackUnlessControllerControlsMoreLandsEffect extends RestrictionEffect {

    CantAttackUnlessControllerControlsMoreLandsEffect() {
        super(Duration.WhileOnBattlefield);
    }

    private CantAttackUnlessControllerControlsMoreLandsEffect(final CantAttackUnlessControllerControlsMoreLandsEffect effect) {
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
            return game.getBattlefield().countAll(new FilterControlledLandPermanent(),
                    source.getControllerId(), game) > game.getBattlefield().countAll(new FilterControlledLandPermanent(),
                    defendingPlayerId, game);
        } else {
            return true;
        }
    }

    @Override
    public CantAttackUnlessControllerControlsMoreLandsEffect copy() {
        return new CantAttackUnlessControllerControlsMoreLandsEffect(this);
    }
}

class CantBlockUnlessControllerControlsMoreLandsEffect extends RestrictionEffect {

    CantBlockUnlessControllerControlsMoreLandsEffect() {
        super(Duration.WhileOnBattlefield);
    }

    private CantBlockUnlessControllerControlsMoreLandsEffect(final CantBlockUnlessControllerControlsMoreLandsEffect effect) {
        super(effect);
    }

    @Override
    public CantBlockUnlessControllerControlsMoreLandsEffect copy() {
        return new CantBlockUnlessControllerControlsMoreLandsEffect(this);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker == null) {
            return true;
        }
        UUID attackingPlayerId = attacker.getControllerId();
        if (attackingPlayerId != null) {
            return game.getBattlefield().countAll(new FilterControlledLandPermanent(),
                    source.getControllerId(), game) > game.getBattlefield().countAll(new FilterControlledLandPermanent(),
                    attackingPlayerId, game);
        }
        return true;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }
}
