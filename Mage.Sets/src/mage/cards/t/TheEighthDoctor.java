package mage.cards.t;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.replacement.LeaveBattlefieldExileSourceReplacementEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;

/**
 *
 * @author muz
 */
public final class TheEighthDoctor extends CardImpl {

    public TheEighthDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When The Eighth Doctor enters the battlefield, mill three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3)));

        // Once during each of your turns, you may play a historic land or cast a historic permanent spell from your graveyard. If you do, it gains "If this permanent would leave the battlefield, exile it instead of putting it anywhere else."
        this.addAbility(new SimpleStaticAbility(new TheEighthDoctorPlayEffect()).setIdentifier(MageIdentifier.TheEighthDoctorWatcher));
        this.addAbility(new TheEighthDoctorTriggeredAbility());
    }

    private TheEighthDoctor(final TheEighthDoctor card) {
        super(card);
    }

    @Override
    public TheEighthDoctor copy() {
        return new TheEighthDoctor(this);
    }
}


class TheEighthDoctorPlayEffect extends AsThoughEffectImpl {

    TheEighthDoctorPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "once during each of your turns, you may play a historic land " +
                "or cast a historic permanent spell from your graveyard. If you do, it gains " +
                "\"If this permanent would leave the battlefield, exile it instead of putting it anywhere else.\"";
    }

    private TheEighthDoctorPlayEffect(final TheEighthDoctorPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TheEighthDoctorPlayEffect copy() {
        return new TheEighthDoctorPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(objectId);
        return card != null
                && card.isOwnedBy(affectedControllerId)
                && source.isControlledBy(affectedControllerId)
                && game.isActivePlayer(affectedControllerId)
                && !TheEighthDoctorWatcher.checkPlayer(source, game)
                && ((card.isHistoric(game) && card.isLand(game)) || (card.isHistoric(game) && card.isPermanent(game)))
                && Zone.GRAVEYARD.match(game.getState().getZone(card.getId()));
    }
}


class TheEighthDoctorTriggeredAbility extends TriggeredAbilityImpl {

    TheEighthDoctorTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
        this.usesStack = false;
        this.setRuleVisible(false);
        this.addWatcher(new TheEighthDoctorWatcher());
    }

    private TheEighthDoctorTriggeredAbility(final TheEighthDoctorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheEighthDoctorTriggeredAbility copy() {
        return new TheEighthDoctorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getPlayerId())
                && event.hasApprovingIdentifier(MageIdentifier.TheEighthDoctorWatcher)) {
            this.getEffects().clear();
            this.addEffect(new TheEighthDoctorGainEffect(new MageObjectReference(event.getSourceId(), game)));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "";
    }
}

class TheEighthDoctorGainEffect extends ContinuousEffectImpl {

    private final MageObjectReference mor;
    private final Ability ability = new SimpleStaticAbility(new LeaveBattlefieldExileSourceReplacementEffect("this permanent"));

    TheEighthDoctorGainEffect(MageObjectReference mor) {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        this.mor = mor;
        this.staticText = "If this permanent would leave the battlefield, exile it instead of putting it anywhere else.";
    }

    private TheEighthDoctorGainEffect(final TheEighthDoctorGainEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public TheEighthDoctorGainEffect copy() {
        return new TheEighthDoctorGainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (mor.getZoneChangeCounter() + 1 < game.getState().getZoneChangeCounter(mor.getSourceId())) {
            discard();
            return false;
        }
        Spell spell = game.getSpell(mor.getSourceId());
        if (spell != null) {
            game.getState().addOtherAbility(spell.getCard(), ability);
            return true;
        }
        Permanent permanent = game.getPermanent(mor.getSourceId());
        if (permanent != null) {
            permanent.addAbility(ability, source.getSourceId(), game);
            return true;
        }
        return false;
    }
}

class TheEighthDoctorWatcher extends Watcher {

    private final Map<MageObjectReference, Set<UUID>> map = new HashMap<>();

    TheEighthDoctorWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if ((event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.LAND_PLAYED)
                && event.hasApprovingIdentifier(MageIdentifier.TheEighthDoctorWatcher)) {
            map.computeIfAbsent(
                    event.getApprovingObject()
                            .getApprovingMageObjectReference(),
                    x -> new HashSet<>()
            ).add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean checkPlayer(Ability source, Game game) {
        return game
                .getState()
                .getWatcher(TheEighthDoctorWatcher.class)
                .map
                .getOrDefault(new MageObjectReference(source.getSourceId(), game), Collections.emptySet())
                .contains(source.getControllerId());
    }
}
