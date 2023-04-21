package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class CunningAbduction extends CardImpl {

    public CunningAbduction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{B}");

        // Target opponent reveals their hand. You choose a nonland card from that player's hand and exile it. You may cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new CunningAbductionExileEffect());
    }

    private CunningAbduction(final CunningAbduction card) {
        super(card);
    }

    @Override
    public CunningAbduction copy() {
        return new CunningAbduction(this);
    }
}

class CunningAbductionExileEffect extends OneShotEffect {

    private static final FilterNonlandCard filter = new FilterNonlandCard();

    public CunningAbductionExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent reveals their hand. You choose a nonland card from that player's hand and exile it. You may cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell";
    }

    public CunningAbductionExileEffect(final CunningAbductionExileEffect effect) {
        super(effect);
    }

    @Override
    public CunningAbductionExileEffect copy() {
        return new CunningAbductionExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source);
        if (opponent != null && sourceObject != null) {
            opponent.revealCards(sourceObject.getName(), opponent.getHand(), game);
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                int cardsHand = opponent.getHand().count(filter, game);
                Card card = null;
                if (cardsHand > 0) {
                    TargetCard target = new TargetCard(Zone.HAND, filter);
                    if (controller.choose(Outcome.Benefit, opponent.getHand(), target, source, game)) {
                        card = opponent.getHand().get(target.getFirstTarget(), game);
                    }
                }
                if (card != null) {
                    // move card to exile
                    UUID exileId = CardUtil.getCardExileZoneId(game, source);
                    controller.moveCardsToExile(card, source, game, true, exileId, sourceObject.getIdName());
                    // allow to cast the card
                    ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.Custom);
                    effect.setTargetPointer(new FixedTarget(card, game));
                    game.addEffect(effect, source);
                    // and you may spend mana as though it were mana of any color to cast it
                    effect = new CunningAbductionSpendAnyManaEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId(), game));
                    game.addEffect(effect, source);
                }
                return true;
            }
        }
        return false;
    }
}

class CunningAbductionSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public CunningAbductionSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast it";
    }

    public CunningAbductionSpendAnyManaEffect(final CunningAbductionSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CunningAbductionSpendAnyManaEffect copy() {
        return new CunningAbductionSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards
        if (objectId.equals(((FixedTarget) getTargetPointer()).getTarget())
                && game.getState().getZoneChangeCounter(objectId) <= ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {
            // if the card moved from exile to spell the zone change counter is increased by 1 (effect must applies before and on stack, use isCheckPlayableMode?)
            return source.isControlledBy(affectedControllerId);
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
