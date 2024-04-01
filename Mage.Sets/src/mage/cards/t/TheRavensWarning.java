package mage.cards.t;

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
import mage.game.events.DamagedBatchForOnePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BlueBirdToken;

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
                new TheRavensWarningTriggeredAbility(), false
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
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {

        DamagedBatchForOnePlayerEvent dEvent = (DamagedBatchForOnePlayerEvent) event;

        int flyingDamage = dEvent.getEvents()
                .stream()
                .filter(ev -> ev.getSourceId().equals(controllerId))
                .filter(ev -> {
                    Permanent permanent = game.getPermanentOrLKIBattlefield(ev.getSourceId());
                    return permanent.isCreature() && permanent.hasAbility(FlyingAbility.getInstance(), game);
                })
                .mapToInt(GameEvent::getAmount)
                .sum();

        return flyingDamage > 0 && dEvent.isCombatDamage();
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control with flying deal combat damage to a player this turn," +
                " look at that player's hand and draw a card.";
    }
}
