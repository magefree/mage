package mage.cards.t;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThunderOfUnity extends CardImpl {

    public ThunderOfUnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{W}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- You draw two cards and you lose 2 life.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new DrawCardSourceControllerEffect(2, true),
                new LoseLifeSourceControllerEffect(2).concatBy("and")
        );

        // II, III -- Whenever a creature you control enters this turn, each opponent loses 1 life and you gain 1 life.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
                new CreateDelayedTriggeredAbilityEffect(new ThunderOfUnityTriggeredAbility())
        );
        this.addAbility(sagaAbility);
    }

    private ThunderOfUnity(final ThunderOfUnity card) {
        super(card);
    }

    @Override
    public ThunderOfUnity copy() {
        return new ThunderOfUnity(this);
    }
}

class ThunderOfUnityTriggeredAbility extends DelayedTriggeredAbility {

    ThunderOfUnityTriggeredAbility() {
        super(new LoseLifeOpponentsEffect(1), Duration.EndOfTurn, false, false);
        this.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.setTriggerPhrase("Whenever a creature you control enters this turn, ");
    }

    private ThunderOfUnityTriggeredAbility(final ThunderOfUnityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThunderOfUnityTriggeredAbility copy() {
        return new ThunderOfUnityTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.isCreature(game) && permanent.isControlledBy(getControllerId());
    }
}
