package mage.cards.k;

import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.keyword.TransformAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class KumanoFacesKakkazan extends CardImpl {

    public KumanoFacesKakkazan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.e.EtchingOfKumano.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Kumano Faces Kakkazan deals 1 damage to each opponent and each planeswalker they control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new KumanoFacesKakkazanDamageEffect());

        // II — When you cast your next creature spell this turn, that creature enters the battlefield with an additional +1/+1 counter on it.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new CreateDelayedTriggeredAbilityEffect(new KumanoFacesKakkazanTriggeredAbility()));

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private KumanoFacesKakkazan(final KumanoFacesKakkazan card) {
        super(card);
    }

    @Override
    public KumanoFacesKakkazan copy() {
        return new KumanoFacesKakkazan(this);
    }
}

class KumanoFacesKakkazanDamageEffect extends OneShotEffect {

    public KumanoFacesKakkazanDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 1 damage to each opponent and each planeswalker they control";
    }

    private KumanoFacesKakkazanDamageEffect(final KumanoFacesKakkazanDamageEffect effect) {
        super(effect);
    }

    @Override
    public KumanoFacesKakkazanDamageEffect copy() {
        return new KumanoFacesKakkazanDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (opponents.isEmpty()) {
            return false;
        }
        for (UUID opponentId : opponents) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.damage(1, source, game);
            }
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(CardType.PLANESWALKER, game)) {
            if (opponents.contains(permanent.getControllerId())) {
                permanent.damage(1, source, game);
            }
        }
        return true;
    }
}

class KumanoFacesKakkazanTriggeredAbility extends DelayedTriggeredAbility {

    public KumanoFacesKakkazanTriggeredAbility() {
        super(null, Duration.EndOfTurn);
    }

    private KumanoFacesKakkazanTriggeredAbility(final KumanoFacesKakkazanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KumanoFacesKakkazanTriggeredAbility copy() {
        return new KumanoFacesKakkazanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (this.isControlledBy(event.getPlayerId())) {
            Spell spell = game.getSpell(event.getTargetId());
            if (spell != null && spell.isCreature(game)) {
                this.getEffects().clear();
                this.getEffects().add(new KumanoFacesKakkazanCounterEffect(spell.getSourceId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you cast your next creature spell this turn, that creature enters the battlefield with an additional +1/+1 counter on it.";
    }
}

class KumanoFacesKakkazanCounterEffect extends ReplacementEffectImpl {

    private final UUID spellCastId;

    public KumanoFacesKakkazanCounterEffect(UUID spellCastId) {
        super(Duration.EndOfTurn, Outcome.BoostCreature);
        this.spellCastId = spellCastId;
    }

    private KumanoFacesKakkazanCounterEffect(final KumanoFacesKakkazanCounterEffect effect) {
        super(effect);
        this.spellCastId = effect.spellCastId;
    }

    @Override
    public KumanoFacesKakkazanCounterEffect copy() {
        return new KumanoFacesKakkazanCounterEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return spellCastId.equals(event.getTargetId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }
}
