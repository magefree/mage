package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.CardState;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.util.CardUtil;

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
                || (permanent instanceof PermanentToken)
                || !controller.hasOpponent(permanent.getControllerId(), game)) {
            return false;
        }

        return CardUtil.moveCardWithCounter(game, source, controller, permanent, Zone.EXILED, CounterType.ICE.createInstance());
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        return zce.isDiesEvent()
                && zce.getTarget().isCreature(game)
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
        Card card = game.getCard(sourceId);
        if (card == null) {
            return false;
        }
        if (!source.isControlledBy(affectedControllerId)
                || game.getState().getZone(card.getMainCard().getId()) != Zone.EXILED) {
            return false;
        }
        return !card.isLand(game)
                && game.getOpponents(card.getOwnerId()).contains(source.getControllerId())
                && card.getMainCard().getCounters(game).getCount(CounterType.ICE) > 0;
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
                || !game.getOpponents(game.getOwnerId(sourceId)).contains(source.getControllerId())) {
            return false;
        }

        Card card = game.getCard(sourceId);
        if (card == null) {
            return false;
        }
        card = card.getMainCard();

        // card can be in exile or stack zones
        if (game.getState().getZone(card.getId()) == Zone.EXILED) {
            // exile zone
            return card.getCounters(game).getCount(CounterType.ICE) > 0;
        } else {
            // stack zone
            // you must look at exile zone (use LKI to see ice counters from the past)
            CardState cardState;
            if (card instanceof SplitCard) {
                cardState = game.getLastKnownInformationCard(card.getId(), Zone.EXILED);
            } else if (card instanceof AdventureCard) {
                cardState = game.getLastKnownInformationCard(card.getId(), Zone.EXILED);
            } else if (card instanceof ModalDoubleFacesCard) {
                cardState = game.getLastKnownInformationCard(((ModalDoubleFacesCard) card).getLeftHalfCard().getId(), Zone.EXILED);
            } else {
                cardState = game.getLastKnownInformationCard(card.getId(), Zone.EXILED);
            }
            return cardState != null && cardState.getCounters().getCount(CounterType.ICE) > 0;
        }
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        if (mana.getSourceObject().isSnow()) {
            return mana.getFirstAvailable();
        }
        return null;
    }
}
