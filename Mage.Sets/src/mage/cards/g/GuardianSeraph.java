
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author North
 */
public final class GuardianSeraph extends CardImpl {

    public GuardianSeraph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        
        // If a source an opponent controls would deal damage to you, prevent 1 of that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GuardianSeraphEffect()));
    }

    private GuardianSeraph(final GuardianSeraph card) {
        super(card);
    }

    @Override
    public GuardianSeraph copy() {
        return new GuardianSeraph(this);
    }
}

class GuardianSeraphEffect extends PreventionEffectImpl {

    public GuardianSeraphEffect() {
        super(Duration.WhileOnBattlefield, 1, false, false);
        this.staticText = "If a source an opponent controls would deal damage to you, prevent 1 of that damage";
    }

    private GuardianSeraphEffect(final GuardianSeraphEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getControllerId())) {
            UUID sourceControllerId = game.getControllerId(event.getSourceId());
            if (sourceControllerId != null && game.getOpponents(source.getControllerId()).contains(sourceControllerId)) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public GuardianSeraphEffect copy() {
        return new GuardianSeraphEffect(this);
    }
}
