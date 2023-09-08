package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.EmptyCopyApplier;

/**
 *
 * @author North
 */
public final class RenegadeDoppelganger extends CardImpl {

    public RenegadeDoppelganger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Whenever another creature enters the battlefield under your control, you may have Renegade Doppelganger become a copy of that creature until end of turn.
        this.addAbility(new RenegadeDoppelgangerTriggeredAbility());
    }

    private RenegadeDoppelganger(final RenegadeDoppelganger card) {
        super(card);
    }

    @Override
    public RenegadeDoppelganger copy() {
        return new RenegadeDoppelganger(this);
    }
}

class RenegadeDoppelgangerTriggeredAbility extends TriggeredAbilityImpl {

    RenegadeDoppelgangerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RenegadeDoppelgangerEffect(), true);
    }

    private RenegadeDoppelgangerTriggeredAbility(final RenegadeDoppelgangerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RenegadeDoppelgangerTriggeredAbility copy() {
        return new RenegadeDoppelgangerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isCreature(game) && permanent.isControlledBy(this.getControllerId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever another creature enters the battlefield under your control, you may have {this} become a copy of that creature until end of turn.";
    }
}

class RenegadeDoppelgangerEffect extends OneShotEffect {

    public RenegadeDoppelgangerEffect() {
        super(Outcome.Benefit);
        this.staticText = "have {this} become a copy of that creature until end of turn";
    }

    private RenegadeDoppelgangerEffect(final RenegadeDoppelgangerEffect effect) {
        super(effect);
    }

    @Override
    public RenegadeDoppelgangerEffect copy() {
        return new RenegadeDoppelgangerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Permanent targetCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (targetCreature == null || permanent == null) {
            return false;
        }

        game.copyPermanent(Duration.EndOfTurn, targetCreature, permanent.getId(), source, new EmptyCopyApplier());
        return false;
    }
}
