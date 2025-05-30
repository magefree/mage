package mage.cards.a;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.target.common.TargetNonlandPermanent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class AbstruseAppropriation extends CardImpl {

    public AbstruseAppropriation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{B}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Exile target nonland permanent. You may cast that card for as long as it remains exiled, and you may spend colorless mana as though it were mana of any color to cast that spell.
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addEffect(new AbstruseAppropriationEffect());
    }

    private AbstruseAppropriation(final AbstruseAppropriation card) {
        super(card);
    }

    @Override
    public AbstruseAppropriation copy() {
        return new AbstruseAppropriation(this);
    }
}

class AbstruseAppropriationEffect extends OneShotEffect {

    AbstruseAppropriationEffect() {
        super(Outcome.Exile);
        staticText = "Exile target nonland permanent. You may cast that card for as long as it remains exiled, "
                + "and you may spend colorless mana as though it were mana of any color to cast that spell";
    }

    private AbstruseAppropriationEffect(final AbstruseAppropriationEffect effect) {
        super(effect);
    }

    @Override
    public AbstruseAppropriationEffect copy() {
        return new AbstruseAppropriationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        new ExileTargetEffect()
                .setToSourceExileZone(true)
                .setTargetPointer(getTargetPointer().copy())
                .apply(game, source);
        game.processAction();
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null || !game.getState().getZone(card.getId()).equals(Zone.EXILED)) {
            return true;
        }
        card = card.getMainCard(); // for mdfc
        CardUtil.makeCardPlayable(game, source, card, true, Duration.Custom, false);
        game.addEffect(new AbstruseAppropriationAsThoughEffect(new MageObjectReference(card, game)), source);
        return true;
    }
}

class AbstruseAppropriationAsThoughEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    private final MageObjectReference morCard;

    public AbstruseAppropriationAsThoughEffect(MageObjectReference mor) {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        this.staticText = "You may spend colorless mana as though it were mana of any color to cast that spell";
        this.morCard = mor;
    }

    private AbstruseAppropriationAsThoughEffect(final AbstruseAppropriationAsThoughEffect effect) {
        super(effect);
        this.morCard = effect.morCard;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AbstruseAppropriationAsThoughEffect copy() {
        return new AbstruseAppropriationAsThoughEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card currentCard = game.getCard(morCard.getSourceId());
        if (currentCard == null || currentCard.getZoneChangeCounter(game) > morCard.getZoneChangeCounter() + 1) {
            discard();
            return false;
        }
        Zone zone = game.getState().getZone(currentCard.getId());
        if (zone != Zone.STACK && zone != Zone.EXILED) {
            discard();
            return false;
        }
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards
        return source.isControlledBy(affectedControllerId) && Objects.equals(objectId, currentCard.getId());
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        if (mana.getColorless() > 0) {
            return ManaType.COLORLESS;
        }
        return null;
    }
}
