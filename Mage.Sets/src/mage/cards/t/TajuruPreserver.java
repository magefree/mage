
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;

/**
 * @author noxx
 */
public final class TajuruPreserver extends CardImpl {

    public TajuruPreserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Spells and abilities your opponents control can't cause you to sacrifice permanents.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TajuruPreserverEffect()));
    }

    public TajuruPreserver(final TajuruPreserver card) {
        super(card);
    }

    @Override
    public TajuruPreserver copy() {
        return new TajuruPreserver(this);
    }
}

class TajuruPreserverEffect extends ReplacementEffectImpl {

    public TajuruPreserverEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Spells and abilities your opponents control can't cause you to sacrifice permanents";
    }

    public TajuruPreserverEffect(final TajuruPreserverEffect effect) {
        super(effect);
    }

    @Override
    public TajuruPreserverEffect copy() {
        return new TajuruPreserverEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICE_PERMANENT;
    }    

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isControlledBy(source.getControllerId())) {
            MageObject object = game.getObject(event.getSourceId());
            if (object instanceof PermanentCard) {
                if (game.getOpponents(source.getControllerId()).contains(((PermanentCard)object).getControllerId())) {
                    return true;
                }
            }
            if (object instanceof Spell) {
                if (game.getOpponents(source.getControllerId()).contains(((Spell)object).getControllerId())) {
                    return true;
                }
            }
        }
        return false;
    }

}
