
package mage.cards.k;

import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
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
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KessDissidentMageContinuousEffect()), new KessDissidentMageWatcher());
    }

    public KessDissidentMage(final KessDissidentMage card) {
        super(card);
    }

    @Override
    public KessDissidentMage copy() {
        return new KessDissidentMage(this);
    }
}

class KessDissidentMageContinuousEffect extends ContinuousEffectImpl {

    private static final FilterCard filter = new FilterCard("Instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
    }

    KessDissidentMageContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "During each of your turns, you may cast an instant or sorcery card from your graveyard. If a card cast this way would be put into your graveyard, exile it instead";
    }

    KessDissidentMageContinuousEffect(final KessDissidentMageContinuousEffect effect) {
        super(effect);
    }

    @Override
    public KessDissidentMageContinuousEffect copy() {
        return new KessDissidentMageContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (!game.isActivePlayer(player.getId())) {
                return false;
            }
            for (Card card : player.getGraveyard().getCards(filter, game)) {
                ContinuousEffect effect = new KessDissidentMageCastFromGraveyardEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
                effect = new KessDissidentMageReplacementEffect(card.getId());
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class KessDissidentMageCastFromGraveyardEffect extends AsThoughEffectImpl {

    KessDissidentMageCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast an instant or sorcery card from your graveyard";
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
        if (objectId.equals(getTargetPointer().getFirst(game, source))) {
            if (affectedControllerId.equals(source.getControllerId())) {
                if (game.isActivePlayer(source.getControllerId())) {
                    KessDissidentMageWatcher watcher = (KessDissidentMageWatcher) game.getState().getWatchers().get(KessDissidentMageWatcher.class.getSimpleName(), source.getSourceId());
                    if (!(source instanceof FlashbackAbility)) {
                        return !watcher.isAbilityUsed();
                    }
                }
            }
        }
        return false;
    }
}

class KessDissidentMageReplacementEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    KessDissidentMageReplacementEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
        staticText = "If a card cast this way would be put into your graveyard, exile it instead";
    }

    KessDissidentMageReplacementEffect(final KessDissidentMageReplacementEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public KessDissidentMageReplacementEffect copy() {
        return new KessDissidentMageReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(this.cardId);
        if (controller != null && card != null) {
            controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.STACK, true);
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
        return zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getTargetId().equals(this.cardId);
    }
}

class KessDissidentMageWatcher extends Watcher {

    boolean abilityUsed = false;

    KessDissidentMageWatcher() {
        super(KessDissidentMageWatcher.class.getSimpleName(), WatcherScope.CARD);
    }

    KessDissidentMageWatcher(final KessDissidentMageWatcher watcher) {
        super(watcher);
        this.abilityUsed = watcher.abilityUsed;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getZone() == Zone.GRAVEYARD) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell.isInstant() || spell.isSorcery()) {
                Optional<Ability> source = game.getAbility(event.getSourceId(), event.getSourceId());
                abilityUsed = true;
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
        abilityUsed = false;
    }

    public boolean isAbilityUsed() {
        return abilityUsed;
    }
}
