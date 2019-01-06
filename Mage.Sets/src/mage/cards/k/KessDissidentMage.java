package mage.cards.k;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author spjspj
 */
public final class KessDissidentMage extends CardImpl {

    public KessDissidentMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // During each of your turns, you may cast an instant or sorcery card from your graveyard. If a card cast this way would be put into your graveyard this turn, exile it instead.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new KessDissidentMageCastFromGraveyardEffect());
        ability.addEffect(new KessDissidentMageReplacementEffect());
        this.addAbility(ability, new KessDissidentMageWatcher());
    }

    public KessDissidentMage(final KessDissidentMage card) {
        super(card);
    }

    @Override
    public KessDissidentMage copy() {
        return new KessDissidentMage(this);
    }
}

class KessDissidentMageCastFromGraveyardEffect extends AsThoughEffectImpl {

    KessDissidentMageCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "During each of your turns, you may cast an instant or sorcery card from your graveyard";
    }

    KessDissidentMageCastFromGraveyardEffect(final KessDissidentMageCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public KessDissidentMageCastFromGraveyardEffect copy() {
        return new KessDissidentMageCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!(source instanceof FlashbackAbility) && affectedControllerId.equals(source.getControllerId()) && game.isActivePlayer(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null && (card.isInstant() || card.isSorcery())
                    && game.getState().getZone(objectId).equals(Zone.GRAVEYARD)) {
                // check if not already a card was cast this turn with this ability
                KessDissidentMageWatcher watcher = game.getState().getWatcher(KessDissidentMageWatcher.class);
                return watcher != null && !watcher.isAbilityUsed(new MageObjectReference(source.getSourceId(), game));
            }
        }
        return false;
    }
}

class KessDissidentMageReplacementEffect extends ReplacementEffectImpl {

    KessDissidentMageReplacementEffect() {
        super(Duration.EndOfGame, Outcome.Exile);
        staticText = "If a card cast this way would be put into your graveyard, exile it instead";
    }

    KessDissidentMageReplacementEffect(final KessDissidentMageReplacementEffect effect) {
        super(effect);
    }

    @Override
    public KessDissidentMageReplacementEffect copy() {
        return new KessDissidentMageReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(event.getTargetId());
        if (controller != null && card != null) {
            controller.moveCards(card, Zone.EXILED, source, game);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD) {
            KessDissidentMageWatcher watcher = game.getState().getWatcher(KessDissidentMageWatcher.class);
            if (watcher != null && source.getSourceId().equals(watcher.spellCastWasAllowedBy(new MageObjectReference(event.getTargetId(), game)))) {
                return true;
            }
        }
        return false;
    }
}

class KessDissidentMageWatcher extends Watcher {

    // Which kess object did cast which spell from graveyard
    private final Set<MageObjectReference> allowingObjects = new HashSet<>();
    private final Map<MageObjectReference, UUID> castSpells = new HashMap<>();

    KessDissidentMageWatcher() {
        super(KessDissidentMageWatcher.class, WatcherScope.GAME);
    }

    KessDissidentMageWatcher(final KessDissidentMageWatcher watcher) {
        super(watcher);
        this.allowingObjects.addAll(watcher.allowingObjects);
        this.castSpells.putAll(watcher.castSpells);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getZone() == Zone.GRAVEYARD) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (null != event.getAdditionalReference() && spell.isInstant() || spell.isSorcery()) {
                allowingObjects.add(event.getAdditionalReference());
                castSpells.put(new MageObjectReference(spell.getSourceId(), game), event.getAdditionalReference().getSourceId());
            }
        }
    }

    @Override
    public KessDissidentMageWatcher copy() {
        return new KessDissidentMageWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        allowingObjects.clear();
    }

    public boolean isAbilityUsed(MageObjectReference mor) {
        return allowingObjects.contains(mor);
    }

    public UUID spellCastWasAllowedBy(MageObjectReference mor) {
        return castSpells.getOrDefault(mor, null);
    }

}
