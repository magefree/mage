package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class ShadowOfTheEnemy extends CardImpl {

    public ShadowOfTheEnemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}{B}");

        // Exile all creature cards from target player's graveyard.
        // You may cast spells from among those cards for as long as
        // they remain exiled, and mana of any type can be spent to cast them.
        this.getSpellAbility().addEffect(new ShadowOfTheEnemyEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private ShadowOfTheEnemy(final ShadowOfTheEnemy card) {
        super(card);
    }

    @Override
    public ShadowOfTheEnemy copy() {
        return new ShadowOfTheEnemy(this);
    }
}

class ShadowOfTheEnemyEffect extends OneShotEffect {

    ShadowOfTheEnemyEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all creature cards from target player's graveyard. " +
            "You may cast spells from among those cards for as long as " +
            "they remain exiled, and mana of any type can be spent to cast them.";
    }

    private ShadowOfTheEnemyEffect(final ShadowOfTheEnemyEffect effect) {
        super(effect);
    }

    @Override
    public ShadowOfTheEnemyEffect copy() {
        return new ShadowOfTheEnemyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if(player == null || targetPlayer == null || sourceObject == null){
            return false;
        }

        Set<Card> cards =
            targetPlayer
                .getGraveyard()
                .getCards(StaticFilters.FILTER_CARD_CREATURE, game);

        if(cards.isEmpty()){
            return true;
        }

        player.moveCardsToExile(cards, source, game, true, source.getSourceId(), sourceObject.getName());
        for (Card card : cards) {
            // allow to cast the card
            ContinuousEffect effect = new ShadowOfTheEnemyCastFromExileEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), game));
            game.addEffect(effect, source);
            // and you may spend mana as though it were mana of any color to cast it
            effect = new ShadowOfTheEnemySpendManaEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class ShadowOfTheEnemyCastFromExileEffect extends AsThoughEffectImpl {

    public ShadowOfTheEnemyCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast spells from among them as long as they remain exiled";
    }

    public ShadowOfTheEnemyCastFromExileEffect(final ShadowOfTheEnemyCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ShadowOfTheEnemyCastFromExileEffect copy() {
        return new ShadowOfTheEnemyCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID cardId = getTargetPointer().getFirst(game, source);
        if (cardId == null) {
            this.discard(); // card is no longer in the origin zone, effect can be discarded
            return false;
        }
        Card theCard = game.getCard(objectId);
        if (theCard == null || theCard.isLand(game)) {
            return false;
        }
        objectId = theCard.getMainCard().getId(); // for split cards

        if (objectId.equals(cardId)
            && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            return card != null;
        }
        return false;
    }
}

class ShadowOfTheEnemySpendManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public ShadowOfTheEnemySpendManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "and you may spend mana as though it were mana of any type to cast those spells";
    }

    public ShadowOfTheEnemySpendManaEffect(final ShadowOfTheEnemySpendManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ShadowOfTheEnemySpendManaEffect copy() {
        return new ShadowOfTheEnemySpendManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards
        if (objectId.equals(((FixedTarget) getTargetPointer()).getTarget())
            && game.getState().getZoneChangeCounter(objectId) <= ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {
            // if the card moved from exile to spell the zone change counter is increased by 1
            // (effect must applies before and on stack, use isCheckPlayableMode?)
            return source.isControlledBy(affectedControllerId);
        } else {
            if (((FixedTarget) getTargetPointer()).getTarget().equals(objectId)) {
                // object has moved zone so effect can be discarded
                this.discard();
            }
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
