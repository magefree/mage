package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheLastRonin extends CardImpl {

    public TheLastRonin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Destroy all creatures.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES)
        );

        // II -- Mill four cards. When you do, return target creature card from your graveyard to your hand.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new TheLastRoninEffect());

        // III -- Whenever a creature you control attacks alone this turn, put three +1/+1 counters on it. It gains trample, lifelink, and indestructible until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new CreateDelayedTriggeredAbilityEffect(new TheLastRoninTriggeredAbility())
        );
        this.addAbility(sagaAbility);
    }

    private TheLastRonin(final TheLastRonin card) {
        super(card);
    }

    @Override
    public TheLastRonin copy() {
        return new TheLastRonin(this);
    }
}

class TheLastRoninEffect extends OneShotEffect {

    TheLastRoninEffect() {
        super(Outcome.Benefit);
        staticText = "mill four cards. When you do, return target creature card from your graveyard to your hand";
    }

    private TheLastRoninEffect(final TheLastRoninEffect effect) {
        super(effect);
    }

    @Override
    public TheLastRoninEffect copy() {
        return new TheLastRoninEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.millCards(4, source, game);
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

class TheLastRoninTriggeredAbility extends DelayedTriggeredAbility {

    TheLastRoninTriggeredAbility() {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)).withTargetDescription("it"), Duration.EndOfTurn, false, false);
        this.setTriggerPhrase("Whenever a creature you control attacks alone this turn, ");
        this.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()).setText("It gains trample"));
        this.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance()).setText(", lifelink"));
        this.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()).setText(", and indestructible until end of turn"));
    }

    private TheLastRoninTriggeredAbility(final TheLastRoninTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheLastRoninTriggeredAbility copy() {
        return new TheLastRoninTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getCombat().attacksAlone()) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent == null || !permanent.isControlledBy(getControllerId())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }
}
