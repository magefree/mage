package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShowdownOfTheSkalds extends CardImpl {

    public ShowdownOfTheSkalds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Exile the top four cards of your library. Until the end of your next turn, you may play those cards.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new ShowdownOfTheSkaldsEffect());

        // II, III — Whenever you cast a spell this turn, put a +1/+1 counter on target creature you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
                new CreateDelayedTriggeredAbilityEffect(new ShowdownOfTheSkaldsDelayedTriggeredAbility())
        );
        this.addAbility(sagaAbility);
    }

    private ShowdownOfTheSkalds(final ShowdownOfTheSkalds card) {
        super(card);
    }

    @Override
    public ShowdownOfTheSkalds copy() {
        return new ShowdownOfTheSkalds(this);
    }
}

class ShowdownOfTheSkaldsEffect extends OneShotEffect {

    ShowdownOfTheSkaldsEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile the top four cards of your library. " +
                "Until the end of your next turn, you may play those cards";
    }

    private ShowdownOfTheSkaldsEffect(final ShowdownOfTheSkaldsEffect effect) {
        super(effect);
    }

    @Override
    public ShowdownOfTheSkaldsEffect copy() {
        return new ShowdownOfTheSkaldsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cards = controller.getLibrary().getTopCards(game, 4);
            Card sourceCard = game.getCard(source.getSourceId());
            controller.moveCardsToExile(cards, source, game, true, CardUtil.getCardExileZoneId(game, source), sourceCard != null ? sourceCard.getIdName() : "");

            for (Card card : cards) {
                ContinuousEffect effect = new ShowdownOfTheSkaldsMayPlayEffect();
                effect.setTargetPointer(new FixedTarget(card, game));
                game.addEffect(effect, source);
            }

            return true;
        }
        return false;
    }
}

class ShowdownOfTheSkaldsMayPlayEffect extends AsThoughEffectImpl {

    private int castOnTurn = 0;

    ShowdownOfTheSkaldsMayPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "Until the end of your next turn, you may play that card.";
    }

    private ShowdownOfTheSkaldsMayPlayEffect(final ShowdownOfTheSkaldsMayPlayEffect effect) {
        super(effect);
        castOnTurn = effect.castOnTurn;
    }

    @Override
    public ShowdownOfTheSkaldsMayPlayEffect copy() {
        return new ShowdownOfTheSkaldsMayPlayEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        castOnTurn = game.getTurnNum();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (castOnTurn != game.getTurnNum() && game.getPhase().getStep().getType() == PhaseStep.END_TURN) {
            return game.isActivePlayer(source.getControllerId());
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        UUID objectIdToCast = CardUtil.getMainCardId(game, sourceId);
        return source.isControlledBy(affectedControllerId)
                && getTargetPointer().getTargets(game, source).contains(objectIdToCast);
    }
}

class ShowdownOfTheSkaldsDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ShowdownOfTheSkaldsDelayedTriggeredAbility() {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), Duration.EndOfTurn, false, false);
        this.addTarget(new TargetControlledCreaturePermanent());
    }

    private ShowdownOfTheSkaldsDelayedTriggeredAbility(final ShowdownOfTheSkaldsDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShowdownOfTheSkaldsDelayedTriggeredAbility copy() {
        return new ShowdownOfTheSkaldsDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell this turn, put a +1/+1 counter on target creature you control.";
    }
}
