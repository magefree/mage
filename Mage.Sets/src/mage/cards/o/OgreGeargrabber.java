
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class OgreGeargrabber extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Equipment an opponent controls");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(SubType.EQUIPMENT.getPredicate());
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public OgreGeargrabber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        Ability ability = new AttacksTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn), false);
        ability.addEffect(new OgreGeargrabberEffect1());
        ability.addTarget(new TargetPermanent(1, 1, filter, false));
        this.addAbility(ability);
    }

    private OgreGeargrabber(final OgreGeargrabber card) {
        super(card);
    }

    @Override
    public OgreGeargrabber copy() {
        return new OgreGeargrabber(this);
    }

}

class OgreGeargrabberEffect1 extends OneShotEffect {

    public OgreGeargrabberEffect1() {
        super(Outcome.GainControl);
        staticText = "Attach it to {this}. When you lose control of that Equipment, unattach it.";
    }

    private OgreGeargrabberEffect1(final OgreGeargrabberEffect1 effect) {
        super(effect);
    }

    @Override
    public OgreGeargrabberEffect1 copy() {
        return new OgreGeargrabberEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID equipmentId = source.getFirstTarget();
        if (equipmentId != null) {
            OgreGeargrabberDelayedTriggeredAbility delayedAbility = new OgreGeargrabberDelayedTriggeredAbility(equipmentId);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            Permanent equipment = game.getPermanent(equipmentId);
            if (equipment != null) {
                Permanent ogre = game.getPermanent(source.getSourceId());
                if (ogre != null) {
                    ogre.addAttachment(equipmentId, source, game);
                }
            }
            return true;
        }
        return false;
    }

}

class OgreGeargrabberDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID equipmentId;

    OgreGeargrabberDelayedTriggeredAbility(UUID equipmentId) {
        super(new OgreGeargrabberEffect2(equipmentId));
        this.equipmentId = equipmentId;
    }

    private OgreGeargrabberDelayedTriggeredAbility(final OgreGeargrabberDelayedTriggeredAbility ability) {
        super(ability);
        this.equipmentId = ability.equipmentId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId) && event.getTargetId().equals(equipmentId);
    }

    @Override
    public OgreGeargrabberDelayedTriggeredAbility copy() {
        return new OgreGeargrabberDelayedTriggeredAbility(this);
    }
}

class OgreGeargrabberEffect2 extends OneShotEffect {

    private UUID equipmentId;

    public OgreGeargrabberEffect2(UUID equipmentId) {
        super(Outcome.Neutral);
        this.equipmentId = equipmentId;
        staticText = "When you lose control of that Equipment, unattach it.";
    }

    private OgreGeargrabberEffect2(final OgreGeargrabberEffect2 effect) {
        super(effect);
        this.equipmentId = effect.equipmentId;
    }

    @Override
    public OgreGeargrabberEffect2 copy() {
        return new OgreGeargrabberEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (equipmentId != null) {
            Permanent equipment = game.getPermanent(equipmentId);
            if (equipment != null && equipment.getAttachedTo() != null) {
                Permanent attachedTo = game.getPermanent(equipment.getAttachedTo());
                if (attachedTo != null) {
                    attachedTo.removeAttachment(equipmentId, source, game);
                }
            }
            return true;
        }
        return false;
    }

}
