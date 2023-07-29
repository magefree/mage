package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Optional;

/**
 * @author alexander-novo, Susucr
 */
public class FinalChapterAbilityResolvesTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean rememberSaga;

    public FinalChapterAbilityResolvesTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public FinalChapterAbilityResolvesTriggeredAbility(Effect effect, boolean rememberSaga) {
        super(Zone.BATTLEFIELD, effect, false);
        this.rememberSaga = rememberSaga;
        setTriggerPhrase("Whenever the final chapter ability of a Saga you control resolves, ");
    }

    private FinalChapterAbilityResolvesTriggeredAbility(final FinalChapterAbilityResolvesTriggeredAbility ability) {
        super(ability);
        this.rememberSaga = ability.rememberSaga;
    }

    @Override
    public FinalChapterAbilityResolvesTriggeredAbility copy() {
        return new FinalChapterAbilityResolvesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.RESOLVING_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // At this point, the stack ability no longer exists, so we can only reference
        // the ability that it came from. For EventType.RESOLVING_ABILITY, targetID is
        // the ID of the original ability (on the permanent) that the resolving ability
        // came from.
        Optional<Ability> ability_opt = game.getAbility(event.getTargetId(), event.getSourceId());
        if (!ability_opt.isPresent())
            return false;

        // Make sure it was a triggered ability (needed for checking if it's a chapter
        // ability)
        Ability ability = ability_opt.get();
        if (!(ability instanceof TriggeredAbility))
            return false;

        // Make sure it was a chapter ability
        TriggeredAbility triggeredAbility = (TriggeredAbility) ability;
        if (!SagaAbility.isChapterAbility(triggeredAbility))
            return false;

        // There's a chance that the permanent that this abiltiy came from no longer
        // exists, so try and find it on the battlefield or check last known
        // information.
        // This permanent is needed to check if the resolving ability was the final
        // chapter ability on that permanent.
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent == null
            || !permanent.isControlledBy(getControllerId())
            || !permanent.hasSubtype(SubType.SAGA, game)) {
            return false;
        }

        // Find the max chapter number from that permanent
        int maxChapter = CardUtil
            .castStream(permanent.getAbilities(game).stream(), SagaAbility.class)
            .map(SagaAbility::getMaxChapter)
            .mapToInt(SagaChapter::getNumber)
            .sum();

        // Check if the ability was the last one
        if (!SagaAbility.isFinalAbility(triggeredAbility, maxChapter)) {
            return false;
        }

        if (rememberSaga) {
            getEffects().setTargetPointer(new FixedTarget(permanent.getId(), game));
        }
        return true;
    }
}
