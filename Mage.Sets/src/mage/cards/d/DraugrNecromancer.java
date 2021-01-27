package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.ManaPoolItem;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DraugrNecromancer extends CardImpl {

    public DraugrNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // If a nontoken creature an opponent controls would die, exile that card with an ice counter on it instead.
        this.addAbility(new SimpleStaticAbility(new DraugrNecromancerReplacementEffect()));

        // You may cast spells from among cards in exile your opponents own with ice counters on them, and you may spend mana from snow sources as though it were mana of any color to cast those spells.
        Ability ability = new SimpleStaticAbility(new DraugrNecromancerCastFromExileEffect());
        ability.addEffect(new DraugrNecromancerSpendAnyManaEffect());
        this.addAbility(ability);
    }

    private DraugrNecromancer(final DraugrNecromancer card) {
        super(card);
    }

    @Override
    public DraugrNecromancer copy() {
        return new DraugrNecromancer(this);
    }
}

class DraugrNecromancerReplacementEffect extends ReplacementEffectImpl {

    DraugrNecromancerReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a nontoken creature an opponent controls would die, " +
                "exile that card with an ice counter on it instead";
    }

    private DraugrNecromancerReplacementEffect(final DraugrNecromancerReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DraugrNecromancerReplacementEffect copy() {
        return new DraugrNecromancerReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null
                || permanent == null
                || !controller.hasOpponent(permanent.getControllerId(), game)) {
            return false;
        }
        Card card = game.getCard(permanent.getId());
        controller.moveCards(permanent, Zone.EXILED, source, game);
        card.addCounters(CounterType.ICE.createInstance(), source.getControllerId(), source, game);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        return zce.isDiesEvent()
                && zce.getTarget().isCreature()
                && !(zce.getTarget() instanceof PermanentToken);
    }
}

class DraugrNecromancerCastFromExileEffect extends AsThoughEffectImpl {

    DraugrNecromancerCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may cast spells from among cards in exile your opponents own with ice counters on them";
    }

    private DraugrNecromancerCastFromExileEffect(final DraugrNecromancerCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DraugrNecromancerCastFromExileEffect copy() {
        return new DraugrNecromancerCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)
                || game.getState().getZone(sourceId) != Zone.EXILED) {
            return false;
        }
        Card card = game.getCard(sourceId);
        return card != null
                && !card.isLand()
                && game.getOpponents(card.getOwnerId()).contains(source.getControllerId())
                && card.getCounters(game).getCount(CounterType.ICE) > 0;
    }
}

class DraugrNecromancerSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    DraugrNecromancerSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = ", and you may spend mana from snow sources as though it were mana of any color to cast those spells";
    }

    private DraugrNecromancerSpendAnyManaEffect(final DraugrNecromancerSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DraugrNecromancerSpendAnyManaEffect copy() {
        return new DraugrNecromancerSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)
                || game.getState().getZone(sourceId) != Zone.EXILED) {
            return false;
        }
        Card card = game.getCard(sourceId);
        return card != null
                && !card.isLand()
                && game.getOpponents(card.getOwnerId()).contains(source.getControllerId())
                && card.getCounters(game).getCount(CounterType.ICE) > 0;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        if (mana.getSourceObject().isSnow()) {
            return mana.getFirstAvailable();
        }
        return manaType;
    }
}
