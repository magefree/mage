
package mage.cards.g;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
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
import mage.watchers.common.AttackedLastTurnWatcher;

/**
 *
 * @author TheElk801
 */
public final class GiantTurtle extends CardImpl {

    public GiantTurtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Giant Turtle can't attack if it attacked during your last turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackIfAttackedLastTurnEffect()), new AttackedLastTurnWatcher());
    }

    public GiantTurtle(final GiantTurtle card) {
        super(card);
    }

    @Override
    public GiantTurtle copy() {
        return new GiantTurtle(this);
    }
}

class CantAttackIfAttackedLastTurnEffect extends RestrictionEffect {

    public CantAttackIfAttackedLastTurnEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack if it attacked during your last turn";
    }

    public CantAttackIfAttackedLastTurnEffect(final CantAttackIfAttackedLastTurnEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game) {
        AttackedLastTurnWatcher watcher = (AttackedLastTurnWatcher) game.getState().getWatchers().get(AttackedLastTurnWatcher.class.getSimpleName());
        if (watcher != null) {
            Set<MageObjectReference> attackingCreatures = watcher.getAttackedLastTurnCreatures(attacker.getControllerId());
            MageObjectReference mor = new MageObjectReference(attacker, game);
            if (attackingCreatures.contains(mor)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public CantAttackIfAttackedLastTurnEffect copy() {
        return new CantAttackIfAttackedLastTurnEffect(this);
    }

}
