package mage.cards.o;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AssassinMenaceToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OriginOfTheHiddenOnes extends CardImpl {

    public OriginOfTheHiddenOnes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Origin of the Hidden Ones deals 4 damage to any target.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new DamageTargetEffect(4), new TargetAnyTarget()
        );

        // II -- Create two 1/1 black Assassin creature tokens with menace.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new CreateTokenEffect(new AssassinMenaceToken(), 2)
        );

        // III -- Whenever an Assassin you control attacks this turn, create a 1/1 black Assassin creature token with menace that's tapped and attacking.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new CreateDelayedTriggeredAbilityEffect(new OriginOfTheHiddenOnesTriggeredAbility())
        );
        this.addAbility(sagaAbility);
    }

    private OriginOfTheHiddenOnes(final OriginOfTheHiddenOnes card) {
        super(card);
    }

    @Override
    public OriginOfTheHiddenOnes copy() {
        return new OriginOfTheHiddenOnes(this);
    }
}

class OriginOfTheHiddenOnesTriggeredAbility extends DelayedTriggeredAbility {

    OriginOfTheHiddenOnesTriggeredAbility() {
        super(new CreateTokenEffect(new AssassinMenaceToken(), 1, true, true), Duration.EndOfTurn, false, false);
        setTriggerPhrase("Whenever an Assassin you control attacks this turn, ");
    }

    private OriginOfTheHiddenOnesTriggeredAbility(final OriginOfTheHiddenOnesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OriginOfTheHiddenOnesTriggeredAbility copy() {
        return new OriginOfTheHiddenOnesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null
                && permanent.isControlledBy(getControllerId())
                && permanent.hasSubtype(SubType.ASSASSIN, game);
    }
}
