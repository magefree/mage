package mage.cards.g;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class GontiLordOfLuxury extends CardImpl {

    protected static final String VALUE_PREFIX = "ExileZones";

    public GontiLordOfLuxury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Gonti, Lord of Luxury enters the battlefield, look at the top four cards of target opponent's library, exile one of them face down,
        // then put the rest on the bottom of that library in a random order. For as long as that card remains exiled,
        // you may look at it, you may cast it, and you may spend mana as though it were mana of any type to cast it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GontiLordOfLuxuryEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new GontiLordOfLuxuryLookEffect()));
    }

    public GontiLordOfLuxury(final GontiLordOfLuxury card) {
        super(card);
    }

    @Override
    public GontiLordOfLuxury copy() {
        return new GontiLordOfLuxury(this);
    }
}

class GontiLordOfLuxuryEffect extends OneShotEffect {

    public GontiLordOfLuxuryEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top four cards of target opponent's library, exile one of them face down, then put the rest on the bottom of that library in a random order. For as long as that card remains exiled, you may look at it, you may cast it, and you may spend mana as though it were mana of any type to cast it";
    }

    public GontiLordOfLuxuryEffect(final GontiLordOfLuxuryEffect effect) {
        super(effect);
    }

    @Override
    public GontiLordOfLuxuryEffect copy() {
        return new GontiLordOfLuxuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && opponent != null && sourceObject != null) {
            Cards topCards = new CardsImpl();
            topCards.addAll(opponent.getLibrary().getTopCards(game, 4));
            TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to exile"));
            if (controller.choose(outcome, topCards, target, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    topCards.remove(card);
                    // move card to exile
                    UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                    card.setFaceDown(true, game);
                    if (controller.moveCardsToExile(card, source, game, false, exileZoneId, sourceObject.getIdName())) {
                        card.setFaceDown(true, game);
                        Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(GontiLordOfLuxury.VALUE_PREFIX + source.getSourceId().toString());
                        if (exileZones == null) {
                            exileZones = new HashSet<>();
                            game.getState().setValue(GontiLordOfLuxury.VALUE_PREFIX + source.getSourceId().toString(), exileZones);
                        }
                        exileZones.add(exileZoneId);
                        // allow to cast the card
                        ContinuousEffect effect = new GontiLordOfLuxuryCastFromExileEffect();
                        effect.setTargetPointer(new FixedTarget(card.getId(), game));
                        game.addEffect(effect, source);
                        // and you may spend mana as though it were mana of any color to cast it
                        effect = new GontiLordOfLuxurySpendAnyManaEffect();
                        effect.setTargetPointer(new FixedTarget(card.getId(), game));
                        game.addEffect(effect, source);
                    }
                }
            }
            // then put the rest on the bottom of that library in a random order
            controller.putCardsOnBottomOfLibrary(topCards, game, source, false);
            return true;
        }

        return false;
    }

}

class GontiLordOfLuxuryCastFromExileEffect extends AsThoughEffectImpl {

    public GontiLordOfLuxuryCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell";
    }

    public GontiLordOfLuxuryCastFromExileEffect(final GontiLordOfLuxuryCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GontiLordOfLuxuryCastFromExileEffect copy() {
        return new GontiLordOfLuxuryCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId == null) {
            this.discard();
        } else if (objectId.equals(targetId)
                && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            // TODO: Allow to cast Zoetic Cavern face down
            return card != null
                    && !card.isLand();
        }
        return false;
    }
}

class GontiLordOfLuxurySpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public GontiLordOfLuxurySpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast it";
    }

    public GontiLordOfLuxurySpendAnyManaEffect(final GontiLordOfLuxurySpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GontiLordOfLuxurySpendAnyManaEffect copy() {
        return new GontiLordOfLuxurySpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card theCard = game.getCard(objectId);
        if(theCard == null){
            return false;
        }
        Card mainCard = theCard.getMainCard();
        if(mainCard == null){
            return false;
        }
        objectId = mainCard.getId(); // for split cards
        if (objectId.equals(((FixedTarget) getTargetPointer()).getTarget())
                && game.getState().getZoneChangeCounter(objectId) <= ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {
            if (affectedControllerId.equals(source.getControllerId())) {
                // if the card moved from exile to spell the zone change counter is increased by 1
                if (game.getState().getZoneChangeCounter(objectId) == ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {
                    return true;
                }
            }
        } else if (((FixedTarget) getTargetPointer()).getTarget().equals(objectId)) {
            // object has moved zone so effect can be discarted
            this.discard();
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}

class GontiLordOfLuxuryLookEffect extends AsThoughEffectImpl {

    public GontiLordOfLuxuryLookEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at the cards exiled with {this}";
    }

    public GontiLordOfLuxuryLookEffect(final GontiLordOfLuxuryLookEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GontiLordOfLuxuryLookEffect copy() {
        return new GontiLordOfLuxuryLookEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card theCard = game.getCard(objectId);
        if(theCard == null){
            return false;
        }
        Card mainCard = theCard.getMainCard();
        if(mainCard == null){
            return false;
        }
        objectId = mainCard.getId(); // for split cards
        if (affectedControllerId.equals(source.getControllerId())
                && game.getState().getZone(objectId) == Zone.EXILED) {
            Player controller = game.getPlayer(source.getControllerId());
            MageObject sourceObject = source.getSourceObject(game);
            if (controller != null
                    && sourceObject != null) {
                Card card = game.getCard(objectId);
                if (card != null
                        && card.isFaceDown(game)) {
                    Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(
                            GontiLordOfLuxury.VALUE_PREFIX + source.getSourceId().toString());
                    if (exileZones != null) {
                        for (ExileZone exileZone : game.getExile().getExileZones()) {
                            if (exileZone.contains(objectId)) {
                                if (!exileZones.contains(exileZone.getId())) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
