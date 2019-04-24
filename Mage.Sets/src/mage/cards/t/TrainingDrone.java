
package mage.cards.t;

import java.util.List;
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

/**
 *
 * @author North
 */
public final class TrainingDrone extends CardImpl {

    public TrainingDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.DRONE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TrainingDroneEffect()));

    }

    public TrainingDrone(final TrainingDrone card) {
        super(card);
    }

    @Override
    public TrainingDrone copy() {
        return new TrainingDrone(this);
    }
}

class TrainingDroneEffect extends RestrictionEffect {

    public TrainingDroneEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless it's equipped";
    }

    public TrainingDroneEffect(final TrainingDroneEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            List<UUID> attachments = permanent.getAttachments();
            for (UUID uuid : attachments) {
                Permanent attached = game.getBattlefield().getPermanent(uuid);
                if (attached.hasSubtype(SubType.EQUIPMENT, game)) {
                    return false;
                }
            }
            return true;
        }
        // don't apply for all other creatures!
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public TrainingDroneEffect copy() {
        return new TrainingDroneEffect(this);
    }
}
