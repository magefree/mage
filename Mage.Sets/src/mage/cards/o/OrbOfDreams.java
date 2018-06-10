
package mage.cards.o;

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
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class OrbOfDreams extends CardImpl {

    public OrbOfDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Permanents enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OrbOfDreamsEffect()));
    }

    public OrbOfDreams(final OrbOfDreams card) {
        super(card);
    }

    @Override
    public OrbOfDreams copy() {
        return new OrbOfDreams(this);
    }

    private static class OrbOfDreamsEffect extends ReplacementEffectImpl {

        OrbOfDreamsEffect() {
            super(Duration.WhileOnBattlefield, Outcome.Tap, false);
            staticText = "Permanents enter the battlefield tapped";
        }

        OrbOfDreamsEffect(final OrbOfDreamsEffect effect) {
            super(effect);
        }

        @Override
        public OrbOfDreamsEffect copy() {
            return new OrbOfDreamsEffect(this);
        }

        @Override
        public boolean replaceEvent(GameEvent event, Ability source, Game game) {
            Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
            if (permanent != null) {
                permanent.setTapped(true);
            }
            return false;
        }

        @Override
        public boolean checksEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
        }

        @Override
        public boolean applies(GameEvent event, Ability source, Game game) {
            return true;
        }

    }
}
