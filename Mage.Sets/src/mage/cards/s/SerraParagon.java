package mage.cards.s;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class SerraParagon extends CardImpl {

    public SerraParagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Once during each of your turns, you may play a land card from your graveyard or cast a permanent spell with mana value 3 or less from your graveyard. If you do, it gains "When this permanent is put into a graveyard from the battlefield, exile it and you gain 2 life."
        this.addAbility(new SimpleStaticAbility(new SerraParagonPlayEffect())
                .setIdentifier(MageIdentifier.SerraParagonWatcher));
        this.addAbility(new SerraParagonTriggeredAbility());
    }

    private SerraParagon(final SerraParagon card) {
        super(card);
    }

    @Override
    public SerraParagon copy() {
        return new SerraParagon(this);
    }
}

class SerraParagonPlayEffect extends AsThoughEffectImpl {

    SerraParagonPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "once during each of your turns, you may play a land card from your graveyard " +
                "or cast a permanent spell with mana value 3 or less from your graveyard. If you do, it gains " +
                "\"When this permanent is put into a graveyard from the battlefield, exile it and you gain 2 life.\"";
    }

    SerraParagonPlayEffect(final SerraParagonPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SerraParagonPlayEffect copy() {
        return new SerraParagonPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(objectId);
        return card != null
                && card.isOwnedBy(affectedControllerId)
                && source.isControlledBy(affectedControllerId)
                && game.isActivePlayer(affectedControllerId)
                && !SerraParagonWatcher.checkPlayer(source, game)
                && (card.isLand(game) || card.getManaValue() <= 3)
                && Zone.GRAVEYARD.match(game.getState().getZone(card.getId()));
    }
}


class SerraParagonTriggeredAbility extends TriggeredAbilityImpl {

    SerraParagonTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
        this.usesStack = false;
        this.setRuleVisible(false);
        this.addWatcher(new SerraParagonWatcher());
    }

    private SerraParagonTriggeredAbility(final SerraParagonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SerraParagonTriggeredAbility copy() {
        return new SerraParagonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getPlayerId())
                && event.hasApprovingIdentifier(MageIdentifier.SerraParagonWatcher)) {
            this.getEffects().clear();
            this.addEffect(new SerraParagonGainEffect(new MageObjectReference(event.getSourceId(), game)));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "";
    }
}

class SerraParagonGainEffect extends ContinuousEffectImpl {

    private final MageObjectReference mor;
    private final Ability ability = new DiesSourceTriggeredAbility(new ExileSourceEffect().setText("exile it")).setTriggerPhrase("When this permanent is put into a graveyard from the battlefield, ");

    {
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
    }

    SerraParagonGainEffect(MageObjectReference mor) {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        this.mor = mor;
    }

    private SerraParagonGainEffect(final SerraParagonGainEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public SerraParagonGainEffect copy() {
        return new SerraParagonGainEffect(this);
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

class SerraParagonWatcher extends Watcher {

    private final Map<MageObjectReference, Set<UUID>> map = new HashMap<>();

    SerraParagonWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if ((event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.LAND_PLAYED)
                && event.hasApprovingIdentifier(MageIdentifier.SerraParagonWatcher)) {
            map.computeIfAbsent(
                    event.getAdditionalReference().getApprovingMageObjectReference(), x -> new HashSet<>()
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
                .getWatcher(SerraParagonWatcher.class)
                .map
                .getOrDefault(new MageObjectReference(source), Collections.emptySet())
                .contains(source.getControllerId());
    }
}
