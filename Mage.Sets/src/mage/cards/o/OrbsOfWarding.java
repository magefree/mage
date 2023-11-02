
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class OrbsOfWarding extends CardImpl {

    public OrbsOfWarding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // You have hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControllerEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield)));

        // If a creature would deal damage to you, prevent 1 of that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OrbsOfWardingEffect()));
    }

    private OrbsOfWarding(final OrbsOfWarding card) {
        super(card);
    }

    @Override
    public OrbsOfWarding copy() {
        return new OrbsOfWarding(this);
    }
}

class OrbsOfWardingEffect extends PreventionEffectImpl {

    public OrbsOfWardingEffect() {
        super(Duration.WhileOnBattlefield, 1, false, false);
        this.staticText = "If a creature would deal damage to you, prevent 1 of that damage";
    }

    private OrbsOfWardingEffect(final OrbsOfWardingEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getControllerId())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (permanent != null && permanent.isCreature(game)) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public OrbsOfWardingEffect copy() {
        return new OrbsOfWardingEffect(this);
    }
}
