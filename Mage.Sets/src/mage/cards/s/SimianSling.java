package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.ReconfigureAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SimianSling extends CardImpl {

    public SimianSling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.MONKEY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // Whenever Simian Sling or equipped creature becomes blocked, it deals 1 damage to defending player.
        this.addAbility(new SimianSlingTriggeredAbility());

        // Reconfigure {2}
        this.addAbility(new ReconfigureAbility("{2}"));
    }

    private SimianSling(final SimianSling card) {
        super(card);
    }

    @Override
    public SimianSling copy() {
        return new SimianSling(this);
    }
}

class SimianSlingTriggeredAbility extends TriggeredAbilityImpl {

    SimianSlingTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SimianSlingEffect());
    }

    private SimianSlingTriggeredAbility(final SimianSlingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SimianSlingTriggeredAbility copy() {
        return new SimianSlingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId())) {
            Permanent permanent = getSourcePermanentOrLKI(game);
            if (permanent == null || !event.getTargetId().equals(permanent.getAttachedTo())) {
                return false;
            }
        }
        getEffects().setValue("attacker", event.getTargetId());
        getEffects().setTargetPointer(new FixedTarget(game.getCombat().getDefendingPlayerId(event.getTargetId(), game)));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever Simian Sling or equipped creature becomes blocked, it deals 1 damage to defending player.";
    }
}

class SimianSlingEffect extends OneShotEffect {

    SimianSlingEffect() {
        super(Outcome.Benefit);
    }

    private SimianSlingEffect(final SimianSlingEffect effect) {
        super(effect);
    }

    @Override
    public SimianSlingEffect copy() {
        return new SimianSlingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID attacker = (UUID) getValue("attacker");
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        return attacker != null
                && player != null
                && player.damage(1, attacker, source, game) > 0;
    }
}
