
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetSource;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class OraclesAttendants extends CardImpl {

    public OraclesAttendants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // {tap}: All damage that would be dealt to target creature this turn by a source of your choice is dealt to Oracle's Attendants instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OraclesAttendantsReplacementEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private OraclesAttendants(final OraclesAttendants card) {
        super(card);
    }

    @Override
    public OraclesAttendants copy() {
        return new OraclesAttendants(this);
    }
}

class OraclesAttendantsReplacementEffect extends ReplacementEffectImpl {
    
    private final TargetSource targetSource;

    public OraclesAttendantsReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        this.targetSource = new TargetSource();
        this.staticText = "All damage that would be dealt to target creature this turn by a source of your choice is dealt to {this} instead";
    }
    
    public OraclesAttendantsReplacementEffect(final OraclesAttendantsReplacementEffect effect) {
        super(effect);
        this.targetSource = effect.targetSource.copy();
    }

    @Override
    public OraclesAttendantsReplacementEffect copy() {
        return new OraclesAttendantsReplacementEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.targetSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        DamageEvent damageEvent = (DamageEvent) event;
        if (permanent != null) {
            permanent.damage(damageEvent.getAmount(), damageEvent.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable());
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_PERMANENT;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getFirstTarget())
                && event.getSourceId().equals(targetSource.getFirstTarget());
    }
    
}
