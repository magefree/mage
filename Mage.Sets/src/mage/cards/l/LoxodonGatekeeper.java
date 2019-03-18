
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public final class LoxodonGatekeeper extends CardImpl {

    public LoxodonGatekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Artifacts, creatures, and lands your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LoxodonGatekeeperTapEffect()));

    }

    public LoxodonGatekeeper(final LoxodonGatekeeper card) {
        super(card);
    }

    @Override
    public LoxodonGatekeeper copy() {
        return new LoxodonGatekeeper(this);
    }
}

class LoxodonGatekeeperTapEffect extends ReplacementEffectImpl {

    LoxodonGatekeeperTapEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "Artifacts, creatures, and lands your opponents control enter the battlefield tapped";
    }

    LoxodonGatekeeperTapEffect(final LoxodonGatekeeperTapEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.setTapped(true);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
            if (permanent != null
                    && (permanent.isCreature()
                    || permanent.isLand()
                    || permanent.isArtifact())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public LoxodonGatekeeperTapEffect copy() {
        return new LoxodonGatekeeperTapEffect(this);
    }
}
