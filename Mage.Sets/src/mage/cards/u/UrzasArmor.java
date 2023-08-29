
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Backfir3
 */
public final class UrzasArmor extends CardImpl {

    public UrzasArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // If a source would deal damage to you, prevent 1 of that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UrzasArmorEffect()));
    }

    private UrzasArmor(final UrzasArmor card) {
        super(card);
    }

    @Override
    public UrzasArmor copy() {
        return new UrzasArmor(this);
    }
}

class UrzasArmorEffect extends PreventionEffectImpl {

    public UrzasArmorEffect() {
        super(Duration.WhileOnBattlefield, 1, false, false);
        this.staticText = "If a source would deal damage to you, prevent 1 of that damage";
    }

    private UrzasArmorEffect(final UrzasArmorEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getControllerId())) {
            return super.applies(event, source, game);
        }
        return false;
    }

    @Override
    public UrzasArmorEffect copy() {
        return new UrzasArmorEffect(this);
    }
}
