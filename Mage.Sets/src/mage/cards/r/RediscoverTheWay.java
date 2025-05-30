package mage.cards.r;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RediscoverTheWay extends CardImpl {

    public RediscoverTheWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{R}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Look at the top three cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new LookLibraryAndPickControllerEffect(3, 1, PutCards.HAND, PutCards.BOTTOM_ANY)
        );

        // III -- Whenever you cast a noncreature spell this turn, target creature you control gains double strike until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new CreateDelayedTriggeredAbilityEffect(new RediscoverTheWayTriggeredAbility())
        );
        this.addAbility(sagaAbility);
    }

    private RediscoverTheWay(final RediscoverTheWay card) {
        super(card);
    }

    @Override
    public RediscoverTheWay copy() {
        return new RediscoverTheWay(this);
    }
}

class RediscoverTheWayTriggeredAbility extends DelayedTriggeredAbility {

    RediscoverTheWayTriggeredAbility() {
        super(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()), Duration.EndOfTurn, false, false);
        this.addTarget(new TargetControlledCreaturePermanent());
        this.setTriggerPhrase("Whenever you cast a noncreature spell this turn, ");
    }

    private RediscoverTheWayTriggeredAbility(final RediscoverTheWayTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RediscoverTheWayTriggeredAbility copy() {
        return new RediscoverTheWayTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getSpell(event.getTargetId());
        return spell != null && !spell.isCreature(game);
    }
}
