
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
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
 * @author Quercitron
 */
public final class Kismet extends CardImpl {

    public Kismet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        // Artifacts, creatures, and lands your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KismetEffect()));
    }

    public Kismet(final Kismet card) {
        super(card);
    }

    @Override
    public Kismet copy() {
        return new Kismet(this);
    }
}

class KismetEffect extends ReplacementEffectImpl {

    KismetEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "Artifacts, creatures, and lands your opponents control enter the battlefield tapped";
    }

    KismetEffect(final KismetEffect effect) {
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
            if (permanent != null && (permanent.isArtifact()
                    || permanent.isCreature()
                    || permanent.isLand())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public KismetEffect copy() {
        return new KismetEffect(this);
    }
}
