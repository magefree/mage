package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.*;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BlueBirdToken;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class TheRavensWarning extends CardImpl {

    public TheRavensWarning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Create a 1/1 blue Bird creature token with flying. You gain 2 life.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I,
                new CreateTokenEffect(new BlueBirdToken()), new GainLifeEffect(2)
        );

        // II — Whenever one or more creatures you control with flying deal combat damage to a player this turn,
        // look at that player's hand and draw a card.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new CreateDelayedTriggeredAbilityEffect(
                new TheRavensWarningTriggeredAbility(), false, false
        ));

        // III — You may put a card you own from outside the game on top of your library.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III,
                new WishEffect(true)
        );
        sagaAbility.addHint(OpenSideboardHint.instance);
        this.addAbility(sagaAbility);
    }

    private TheRavensWarning(final TheRavensWarning card) {
        super(card);
    }

    @Override
    public TheRavensWarning copy() {
        return new TheRavensWarning(this);
    }
}

class TheRavensWarningTriggeredAbility extends DelayedTriggeredAbility {

    private final Set<UUID> damagedPlayerIds = new HashSet<>();

    public TheRavensWarningTriggeredAbility() {
        super(new LookAtTargetPlayerHandEffect(), Duration.EndOfTurn, false);
        this.addEffect(new DrawCardSourceControllerEffect(1));
    }

    private TheRavensWarningTriggeredAbility(final TheRavensWarningTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheRavensWarningTriggeredAbility copy() {
        return new TheRavensWarningTriggeredAbility(this);
    }

    // Code based on ControlledCreaturesDealCombatDamagePlayerTriggeredAbility
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRIORITY
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.isControlledBy(this.getControllerId())
                    && !damagedPlayerIds.contains(event.getPlayerId()) && p.hasAbility(FlyingAbility.getInstance(), game)) {
                damagedPlayerIds.add(event.getPlayerId());
                this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                return true;
            }
        }
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRIORITY ||
                (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(getSourceId()))) {
            damagedPlayerIds.clear();
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control with flying deal combat damage to a player this turn," +
                " look at that player's hand and draw a card.";
    }
}
