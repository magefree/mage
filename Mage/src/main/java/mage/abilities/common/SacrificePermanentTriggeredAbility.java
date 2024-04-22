package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author TheElk801, xenohedron
 */
public class SacrificePermanentTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private final SetTargetPointer setTargetPointer;

    private final TargetController sacrificingPlayer;

    /**
     * Whenever you sacrifice a "[filter]", "[effect]".
     * zone = battlefield, setTargetPointer = NONE, optional = false
     */
    public SacrificePermanentTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(Zone.BATTLEFIELD, effect, filter, TargetController.YOU, SetTargetPointer.NONE, false);
    }

    public SacrificePermanentTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter,
                                              TargetController sacrificingPlayer,
                                              SetTargetPointer setTargetPointer, boolean optional) {
        super(zone, effect, optional);
        if (Zone.BATTLEFIELD.match(zone)) {
            setLeavesTheBattlefieldTrigger(true);
        }
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        this.sacrificingPlayer = sacrificingPlayer;
        setTriggerPhrase(generateTriggerPhrase());
    }

    protected SacrificePermanentTriggeredAbility(final SacrificePermanentTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
        this.sacrificingPlayer = ability.sacrificingPlayer;
    }

    @Override
    public SacrificePermanentTriggeredAbility copy() {
        return new SacrificePermanentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (sacrificingPlayer) {
            case YOU:
                if (!event.getPlayerId().equals(getControllerId())) {
                    return false;
                }
                break;
            case OPPONENT:
                Player controller = game.getPlayer(getControllerId());
                if (controller == null || !controller.hasOpponent(event.getPlayerId(), game)) {
                    return false;
                }
                break;
            case ANY:
                break;
            default:
                throw new IllegalArgumentException("Unsupported TargetController in SacrificePermanentTriggeredAbility: " + sacrificingPlayer);
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null || !filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        this.getEffects().setValue("sacrificedPermanent", permanent);
        switch (setTargetPointer) {
            case PERMANENT:
                this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                break;
            case PLAYER:
                this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId(), game));
                break;
            case NONE:
                break;
            default:
                throw new IllegalArgumentException("Unsupported SetTargetPointer in SacrificePermanentTriggeredAbility: " + setTargetPointer);
        }
        return true;
    }

    private String generateTriggerPhrase() {
        String targetControllerText;
        switch (sacrificingPlayer) {
            case YOU:
                targetControllerText = "you sacrifice ";
                break;
            case OPPONENT:
                targetControllerText = "an opponent sacrifices ";
                break;
            case ANY:
                targetControllerText = "a player sacrifices ";
                break;
            default:
                throw new IllegalArgumentException("Unsupported TargetController in SacrificePermanentTriggeredAbility: " + sacrificingPlayer);
        }
        return getWhen() + targetControllerText +  CardUtil.addArticle(filter.getMessage()) + ", ";
    }

}
