package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceCreatureType;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class LongListOfTheEnts extends CardImpl {

    public LongListOfTheEnts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after VI.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_VI);

        // I, II, III, IV, V, VI -- Note a creature type that hasn't been noted for Long List of the Ents. When you cast your next creature spell of that type this turn, that creature enters the battlefield with an additional +1/+1 counter on it.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_VI, new LongListOfTheEntsEffect()
        );

        this.addAbility(sagaAbility.addHint(LongListOfTheEntsHint.instance));
    }

    private LongListOfTheEnts(final LongListOfTheEnts card) {
        super(card);
    }

    @Override
    public LongListOfTheEnts copy() {
        return new LongListOfTheEnts(this);
    }
}

enum LongListOfTheEntsHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        if (ability.getSourcePermanentIfItStillExists(game) == null) {
            return null;
        }
        Set<SubType> subTypes = LongListOfTheEntsEffect.getSubTypes(game, ability);
        if (subTypes.isEmpty()) {
            return "No creature types have been noted yet.";
        }
        return subTypes
                .stream()
                .map(SubType::toString)
                .collect(Collectors.joining(
                        ", ", "Noted creature types: " + subTypes.size() + '(', ")"
                ));
    }

    @Override
    public Hint copy() {
        return this;
    }
}

class LongListOfTheEntsEffect extends OneShotEffect {

    LongListOfTheEntsEffect() {
        super(Outcome.Benefit);
        staticText = "note a creature type that hasn't been noted for {this}. When you cast your next creature spell " +
                "of that type this turn, that creature enters the battlefield with an additional +1/+1 counter on it.";
    }

    private LongListOfTheEntsEffect(final LongListOfTheEntsEffect effect) {
        super(effect);
    }

    @Override
    public LongListOfTheEntsEffect copy() {
        return new LongListOfTheEntsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<String> chosenTypes = this
                .getSubTypes(game, source)
                .stream()
                .map(SubType::toString)
                .collect(Collectors.toSet());
        ChoiceCreatureType choice = new ChoiceCreatureType(source.getSourceObject(game));
        choice.getChoices().removeIf(chosenTypes::contains);
        player.choose(Outcome.BoostCreature, choice, game);
        SubType subType = SubType.fromString(choice.getChoice());
        if (subType == null) {
            return false;
        }
        game.addDelayedTriggeredAbility(new LongListOfTheEntsTriggeredAbility(subType), source);
        return true;
    }

    static Set<SubType> getSubTypes(Game game, Ability source) {
        return (Set<SubType>) game.getState().computeValueIfAbsent(
                "EntList"
                        + source.getSourceId()
                        + source.getSourceObjectZoneChangeCounter(),
                x -> new HashSet<SubType>()
        );
    }
}

class LongListOfTheEntsTriggeredAbility extends DelayedTriggeredAbility {

    private final SubType subType;

    LongListOfTheEntsTriggeredAbility(SubType subType) {
        super(new LongListOfTheEntsCounterEffect(), Duration.EndOfTurn, true, false);
        this.subType = subType;
    }

    private LongListOfTheEntsTriggeredAbility(final LongListOfTheEntsTriggeredAbility ability) {
        super(ability);
        this.subType = ability.subType;
    }

    @Override
    public LongListOfTheEntsTriggeredAbility copy() {
        return new LongListOfTheEntsTriggeredAbility(this);
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
        if (spell == null || !spell.isCreature(game) || !spell.hasSubtype(subType, game)) {
            return false;
        }
        this.getEffects().setValue("spellCast", spell);
        return true;
    }

    @Override
    public String getRule() {
        return "When you cast your next creature spell of that type this turn, that creature enters the battlefield with an additional +1/+1 counter on it.";
    }
}

class LongListOfTheEntsCounterEffect extends ReplacementEffectImpl {

    LongListOfTheEntsCounterEffect() {
        super(Duration.EndOfStep, Outcome.BoostCreature);
    }

    private LongListOfTheEntsCounterEffect(LongListOfTheEntsCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = (Spell) getValue("spellCast");
        return spell != null && event.getTargetId().equals(spell.getCard().getId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(
                    CounterType.P1P1.createInstance(), source.getControllerId(),
                    source, game, event.getAppliedEffects()
            );
            discard();
        }
        return false;
    }

    @Override
    public LongListOfTheEntsCounterEffect copy() {
        return new LongListOfTheEntsCounterEffect(this);
    }
}
