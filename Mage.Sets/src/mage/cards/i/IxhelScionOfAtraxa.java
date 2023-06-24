package mage.cards.i;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class IxhelScionOfAtraxa extends CardImpl {

    public IxhelScionOfAtraxa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN, SubType.ANGEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Toxic 2
        this.addAbility(new ToxicAbility(2));

        // Corrupted — At the beginning of your end step, each opponent who has three or more poison counters
        // exiles the top card of their library face down. You may look at and play those cards for as long as
        // they remain exiled, and you may spend mana as though it were mana of any color to cast those spells.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(
                new IxhelScionOfAtraxaEffect(), false).setAbilityWord(AbilityWord.CORRUPTED)
        );
    }

    private IxhelScionOfAtraxa(final IxhelScionOfAtraxa card) {
        super(card);
    }

    @Override
    public IxhelScionOfAtraxa copy() {
        return new IxhelScionOfAtraxa(this);
    }
}

class IxhelScionOfAtraxaEffect extends OneShotEffect {

    public IxhelScionOfAtraxaEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent who has three or more poison counters exiles the top card of their " +
                "library face down. You may look at and play those cards for as long as they remain exiled, " +
                "and you may spend mana as though it were mana of any color to cast those spells";
    }

    private IxhelScionOfAtraxaEffect(final IxhelScionOfAtraxaEffect effect) {
        super(effect);
    }

    @Override
    public IxhelScionOfAtraxaEffect copy() {
        return new IxhelScionOfAtraxaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        UUID exileZoneId = CardUtil.getExileZoneId(game, sourceObject.getId(), sourceObject.getZoneChangeCounter(game));

        for (UUID opponentId : game.getOpponents(source.getControllerId(), true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || opponent.getCounters().getCount(CounterType.POISON) < 3 || !opponent.getLibrary().hasCards()) {
                continue;
            }
            // move card to exile
            Card topCard = opponent.getLibrary().getFromTop(game);
            topCard.setFaceDown(true, game);
            if (opponent.moveCardsToExile(topCard, source, game, false, exileZoneId, sourceObject.getIdName())) {
                topCard.setFaceDown(true, game);
            }
            game.getState().setValue(topCard.getId().toString() + game.getState().getZoneChangeCounter(topCard.getId()), exileZoneId);
            // you may play that card
            ContinuousEffect effect = new IxhelScionOfAtraxaPlayFromExileEffect();
            effect.setTargetPointer(new FixedTarget(topCard.getId(), game));
            game.addEffect(effect, source);
            // you may spend mana as though it were many of any color to cast it
            effect = new IxhelScionOfAtraxaSpendAnyManaEffect();
            effect.setTargetPointer(new FixedTarget(topCard.getId(), game));
            game.addEffect(effect, source);
            // for as long as that card remains exiled, you may look at it
            effect = new IxhelScionOfAtraxaLookEffect(source.getControllerId());
            effect.setTargetPointer(new FixedTarget(topCard.getId(), game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class IxhelScionOfAtraxaPlayFromExileEffect extends AsThoughEffectImpl {

    public IxhelScionOfAtraxaPlayFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
    }

    private IxhelScionOfAtraxaPlayFromExileEffect(final IxhelScionOfAtraxaPlayFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IxhelScionOfAtraxaPlayFromExileEffect copy() {
        return new IxhelScionOfAtraxaPlayFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId == null) {
            // card is no longer in the origin zone, effect can be discarded
            this.discard();
            return false;
        }
        Card theCard = game.getCard(objectId);
        if (theCard == null ) {
            return false;
        }

        // for split cards
        objectId = theCard.getMainCard().getId();

        if (objectId.equals(targetId)  && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            return card != null;
        }
        return false;
    }
}

class IxhelScionOfAtraxaSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public IxhelScionOfAtraxaSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
    }

    private IxhelScionOfAtraxaSpendAnyManaEffect(final IxhelScionOfAtraxaSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IxhelScionOfAtraxaSpendAnyManaEffect copy() {
        return new IxhelScionOfAtraxaSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card theCard = game.getCard(objectId);
        if (theCard == null) {
            return false;
        }

        // for split cards
        objectId = theCard.getMainCard().getId();

        if (objectId.equals(((FixedTarget) getTargetPointer()).getTarget())
                && game.getState().getZoneChangeCounter(objectId) <= ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {
            // if the card moved from exile to spell the zone change counter is increased by 1 (effect must applies before and on stack, use isCheckPlayableMode?)
            return source.isControlledBy(affectedControllerId);
        } else if (((FixedTarget) getTargetPointer()).getTarget().equals(objectId)) {
            // object has moved zone so effect can be discarded
            this.discard();
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}

class IxhelScionOfAtraxaLookEffect extends AsThoughEffectImpl {

    private final UUID authorizedPlayerId;

    public IxhelScionOfAtraxaLookEffect(UUID authorizedPlayerId) {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        this.authorizedPlayerId = authorizedPlayerId;
    }

    private IxhelScionOfAtraxaLookEffect(final IxhelScionOfAtraxaLookEffect effect) {
        super(effect);
        this.authorizedPlayerId = effect.authorizedPlayerId;
    }

    @Override
    public boolean apply(Game game, Ability source) { return true; }

    @Override
    public IxhelScionOfAtraxaLookEffect copy() { return new IxhelScionOfAtraxaLookEffect(this); }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID cardId = getTargetPointer().getFirst(game, source);

        // card is no longer in the origin zone, effect can be discarded
        if (cardId == null) {
            this.discard();
        }

        return affectedControllerId.equals(authorizedPlayerId) && objectId.equals(cardId);
    }
}
