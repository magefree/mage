

package mage.cards.b;

import java.util.UUID;
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
import mage.watchers.common.BloodthirstWatcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public final class BloodcrazedGoblin extends CardImpl {

    public BloodcrazedGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN, SubType.BERSERKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BloodcrazedGoblinEffect()));
    }

    public BloodcrazedGoblin(final BloodcrazedGoblin card) {
        super(card);
    }

    @Override
    public BloodcrazedGoblin copy() {
        return new BloodcrazedGoblin(this);
    }

}

class BloodcrazedGoblinEffect extends RestrictionEffect {

    public BloodcrazedGoblinEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless an opponent has been dealt damage this turn";
    }

    public BloodcrazedGoblinEffect(final BloodcrazedGoblinEffect effect) {
        super(effect);
    }

    @Override
    public BloodcrazedGoblinEffect copy() {
        return new BloodcrazedGoblinEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            BloodthirstWatcher watcher = (BloodthirstWatcher) game.getState().getWatchers().get(BloodthirstWatcher.class.getSimpleName(), source.getControllerId()); // BloodthirstWatcher
            return !watcher.conditionMet();
        }
        return false;
    }
}
