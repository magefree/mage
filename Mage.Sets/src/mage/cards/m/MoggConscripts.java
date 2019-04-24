
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.PlayerCastCreatureWatcher;

/**
 *
 * @author fireshoes
 */
public final class MoggConscripts extends CardImpl {

    public MoggConscripts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Mogg Conscripts can't attack unless you've cast a creature spell this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MoggConscriptsEffect()), new PlayerCastCreatureWatcher());
    }

    public MoggConscripts(final MoggConscripts card) {
        super(card);
    }

    @Override
    public MoggConscripts copy() {
        return new MoggConscripts(this);
    }
}

class MoggConscriptsEffect extends RestrictionEffect {

    public MoggConscriptsEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you've cast a creature spell this turn";
    }

    public MoggConscriptsEffect(final MoggConscriptsEffect effect) {
        super(effect);
    }

    @Override
    public MoggConscriptsEffect copy() {
        return new MoggConscriptsEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            PlayerCastCreatureWatcher watcher = (PlayerCastCreatureWatcher) game.getState().getWatchers().get(PlayerCastCreatureWatcher.class.getSimpleName());
            if (watcher != null && !watcher.playerDidCastCreatureThisTurn(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }
}
