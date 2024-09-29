
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author fireshoes
 */
public final class GridMonitor extends CardImpl {

    public GridMonitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // You can't cast creature spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GridMonitorEffect()));
    }

    private GridMonitor(final GridMonitor card) {
        super(card);
    }

    @Override
    public GridMonitor copy() {
        return new GridMonitor(this);
    }
}

class GridMonitorEffect extends ContinuousRuleModifyingEffectImpl {
    
    public GridMonitorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You can't cast creature spells";
    }

    private GridMonitorEffect(final GridMonitorEffect effect) {
        super(effect);
    }

    @Override
    public GridMonitorEffect copy() {
        return new GridMonitorEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null && object.isCreature(game)) {
                return true;
            }
        }
        return false;
    }
}