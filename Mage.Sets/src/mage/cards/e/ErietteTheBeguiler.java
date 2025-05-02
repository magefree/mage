package mage.cards.e;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ErietteTheBeguiler extends CardImpl {

    public ErietteTheBeguiler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever an Aura you control becomes attached to a nonland permanent an opponent controls with mana value less than or equal to that Aura's mana value, gain control of that permanent for as long as that Aura is attached to it.
        this.addAbility(new ErietteTheBeguilerTriggeredAbility());
    }

    private ErietteTheBeguiler(final ErietteTheBeguiler card) {
        super(card);
    }

    @Override
    public ErietteTheBeguiler copy() {
        return new ErietteTheBeguiler(this);
    }
}

class ErietteTheBeguilerTriggeredAbility extends TriggeredAbilityImpl {

    ErietteTheBeguilerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ErietteTheBeguilerEffect());
    }

    private ErietteTheBeguilerTriggeredAbility(final ErietteTheBeguilerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ErietteTheBeguilerTriggeredAbility copy() {
        return new ErietteTheBeguilerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent aura = game.getPermanent(event.getSourceId());
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (aura == null
                || permanent == null
                || !aura.isControlledBy(getControllerId())
                || !aura.hasSubtype(SubType.AURA, game)
                || permanent.isLand(game)
                || !game.getOpponents(getControllerId()).contains(permanent.getControllerId())
                || aura.getManaValue() < permanent.getManaValue()) {
            return false;
        }
        this.getEffects().setValue("auraRef", new MageObjectReference(aura, game));
        this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever an Aura you control becomes attached to a nonland permanent " +
                "an opponent controls with mana value less than or equal to that Aura's mana value, " +
                "gain control of that permanent for as long as that Aura is attached to it.";
    }
}

class ErietteTheBeguilerEffect extends GainControlTargetEffect {

    ErietteTheBeguilerEffect() {
        super(Duration.Custom);
    }

    private ErietteTheBeguilerEffect(final ErietteTheBeguilerEffect effect) {
        super(effect);
    }

    @Override
    public ErietteTheBeguilerEffect copy() {
        return new ErietteTheBeguilerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = ((MageObjectReference) getValue("auraRef")).getPermanent(game);
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (aura == null || permanent == null || !permanent.getId().equals(aura.getAttachedTo())) {
            discard();
            return false;
        }
        return super.apply(game, source);
    }
}
