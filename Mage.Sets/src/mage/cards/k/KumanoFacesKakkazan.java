package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.common.DamagedByControlledWatcher;

import java.util.Set;
import java.util.UUID;

/**
 * @author weirddan455
 */
public final class KumanoFacesKakkazan extends TransformingDoubleFacedCard {

    public KumanoFacesKakkazan(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{R}",
                "Etching of Kumano",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SHAMAN}, "R"
        );
        this.getRightHalfCard().setPT(2, 2);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I — Kumano Faces Kakkazan deals 1 damage to each opponent and each planeswalker they control.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I,
                new KumanoFacesKakkazanDamageEffect()
        );

        // II — When you cast your next creature spell this turn, that creature enters the battlefield with an additional +1/+1 counter on it.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_II,
                new CreateDelayedTriggeredAbilityEffect(new KumanoFacesKakkazanTriggeredAbility())
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_III,
                new ExileSagaAndReturnTransformedEffect()
        );
        sagaAbility.addWatcher(new DamagedByControlledWatcher());
        this.getLeftHalfCard().addAbility(sagaAbility);

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // If a creature dealt damage this turn by a source you controlled would die, exile it instead.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new EtchingOfKumanoReplacementEffect()));
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

class EtchingOfKumanoReplacementEffect extends ReplacementEffectImpl {

    public EtchingOfKumanoReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        this.staticText = "If a creature dealt damage this turn by a source you controlled would die, exile it instead";
    }

    private EtchingOfKumanoReplacementEffect(final EtchingOfKumanoReplacementEffect effect) {
        super(effect);
    }

    @Override
    public EtchingOfKumanoReplacementEffect copy() {
        return new EtchingOfKumanoReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        if (zce.isDiesEvent()) {
            DamagedByControlledWatcher watcher = game.getState().getWatcher(DamagedByControlledWatcher.class, source.getControllerId());
            if (watcher != null) {
                return watcher.wasDamaged(zce.getTarget(), game);
            }
        }
        return false;
    }
}
