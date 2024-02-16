package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MedomaisProphecy extends CardImpl {

    public MedomaisProphecy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I — Scry 2.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new ScryEffect(2));

        // II — Choose a card name.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL));

        // III — When you cast a spell with the chosen card name for the first time this turn, draw two cards.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new MedomaisProphecyTriggerEffect());

        // IV — Look at the top card of each player's library.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_IV, new MedomaisProphecyLookEffect());
        this.addAbility(sagaAbility);
    }

    private MedomaisProphecy(final MedomaisProphecy card) {
        super(card);
    }

    @Override
    public MedomaisProphecy copy() {
        return new MedomaisProphecy(this);
    }
}

class MedomaisProphecyTriggerEffect extends OneShotEffect {

    MedomaisProphecyTriggerEffect() {
        super(Outcome.Benefit);
        staticText = "When you cast a spell with the chosen name for the first time this turn, draw two cards.";
    }

    private MedomaisProphecyTriggerEffect(final MedomaisProphecyTriggerEffect effect) {
        super(effect);
    }

    @Override
    public MedomaisProphecyTriggerEffect copy() {
        return new MedomaisProphecyTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) game.getState().getValue(
                source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY
        );
        if (cardName == null || cardName.isEmpty()) {
            return false;
        }
        game.addDelayedTriggeredAbility(new MedomaisProphecyDelayedTriggeredAbility(cardName), source);
        return true;
    }
}

class MedomaisProphecyDelayedTriggeredAbility extends DelayedTriggeredAbility {
    private final String spellName;

    MedomaisProphecyDelayedTriggeredAbility(String spellName) {
        super(new DrawCardSourceControllerEffect(2), Duration.EndOfTurn, true, false);
        this.spellName = spellName;
    }

    private MedomaisProphecyDelayedTriggeredAbility(final MedomaisProphecyDelayedTriggeredAbility ability) {
        super(ability);
        this.spellName = ability.spellName;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spellName.equals(spell.getName());
    }

    @Override
    public MedomaisProphecyDelayedTriggeredAbility copy() {
        return new MedomaisProphecyDelayedTriggeredAbility(this);
    }
}

class MedomaisProphecyLookEffect extends OneShotEffect {

    MedomaisProphecyLookEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of each player's library";
    }

    private MedomaisProphecyLookEffect(final MedomaisProphecyLookEffect effect) {
        super(effect);
    }

    @Override
    public MedomaisProphecyLookEffect copy() {
        return new MedomaisProphecyLookEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourcePermanentOrLKI(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .forEachOrdered(player -> controller.lookAtCards(
                        sourceObject.getIdName() + " " + player.getName(), player.getLibrary().getFromTop(game), game
                ));
        return true;
    }
}
