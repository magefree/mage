package mage.cards.t;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.CastNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenNamePredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.functions.RemoveTypeCopyApplier;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class TheCloneSaga extends CardImpl {



    public TheCloneSaga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Surveil 3.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new SurveilEffect(3));

        // II -- When you next cast a creature spell this turn, copy it, except the copy isn't legendary.
        DelayedTriggeredAbility ability = new CastNextSpellDelayedTriggeredAbility(
                new CopyTargetStackObjectEffect(false, true, false, 1, new RemoveTypeCopyApplier(SuperType.LEGENDARY))
                        .setText("copy it, except the copy isn't legendary"),
                StaticFilters.FILTER_SPELL_A_CREATURE, true
        );
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new CreateDelayedTriggeredAbilityEffect(ability));

        // III -- Choose a card name. Whenever a creature with the chosen name deals combat damage to a player this turn, draw a card.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL),
                new CreateDelayedTriggeredAbilityEffect(new TheCloneSagaDelayedTrigger()));

        this.addAbility(sagaAbility);
    }

    private TheCloneSaga(final TheCloneSaga card) {
        super(card);
    }

    @Override
    public TheCloneSaga copy() {
        return new TheCloneSaga(this);
    }
}
class TheCloneSagaDelayedTrigger extends DelayedTriggeredAbility {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with the chosen name");

    static {
        filter.add(ChosenNamePredicate.instance);
    }

    TheCloneSagaDelayedTrigger() {
        super(new DrawCardSourceControllerEffect(1), Duration.EndOfTurn, false);
    }

    private TheCloneSagaDelayedTrigger(final TheCloneSagaDelayedTrigger ability) {
        super(ability);
    }

    @Override
    public TheCloneSagaDelayedTrigger copy() {
        return new TheCloneSagaDelayedTrigger(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent creature = game.getPermanent(event.getSourceId());
            return creature != null && filter.match(creature, getControllerId(), this, game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature with the chosen name deals combat damage to a player this turn, draw a card.";
    }
}
