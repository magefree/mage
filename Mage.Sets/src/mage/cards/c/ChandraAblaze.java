package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetDiscard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author North
 */
public final class ChandraAblaze extends CardImpl {

    public ChandraAblaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);

        this.setStartingLoyalty(5);

        // +1: Discard a card. If a red card is discarded this way, Chandra Ablaze deals 4 damage to any target.
        LoyaltyAbility ability = new LoyaltyAbility(new ChandraAblazeDiscardCardEffect(), 1);
        ability.addEffect(new ChandraAblazeDamageEffect());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // -2: Each player discards their hand, then draws three cards.
        ability = new LoyaltyAbility(new DiscardHandAllEffect(), -2);
        Effect effect = new DrawCardAllEffect(3);
        effect.setText(", then draws three cards");
        ability.addEffect(effect);
        this.addAbility(ability);

        // -7: Cast any number of red instant and/or sorcery cards from your graveyard without paying their mana costs.
        ability = new LoyaltyAbility(new ChandraAblazeCastCardsEffect(), -7);
        this.addAbility(ability);
    }

    private ChandraAblaze(final ChandraAblaze card) {
        super(card);
    }

    @Override
    public ChandraAblaze copy() {
        return new ChandraAblaze(this);
    }
}

class ChandraAblazeDiscardCardEffect extends OneShotEffect {

    public ChandraAblazeDiscardCardEffect() {
        super(Outcome.Discard);
        this.staticText = "Discard a card";
    }

    private ChandraAblazeDiscardCardEffect(final ChandraAblazeDiscardCardEffect effect) {
        super(effect);
    }

    @Override
    public ChandraAblazeDiscardCardEffect copy() {
        return new ChandraAblazeDiscardCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // If you activate Chandra Ablaze’s first ability, you don’t discard a card until the ability resolves.
        // You may activate the ability even if your hand is empty. You choose a target as you activate the ability
        // even if you have no red cards in hand at that time.
        // (2009-10-01)

        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetDiscard target = new TargetDiscard(player.getId());
            player.choose(Outcome.Discard, target, source, game);
            Card card = player.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                player.discard(card, false, source, game);
                source.getEffects().get(1).setValue("discardedCard", card);
                game.getState().setValue(source.getSourceId().toString(), card);
                return true;
            }
        }
        return false;
    }
}

class ChandraAblazeDamageEffect extends OneShotEffect {

    public ChandraAblazeDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "If a red card is discarded this way, {this} deals 4 damage to any target";
    }

    private ChandraAblazeDamageEffect(final ChandraAblazeDamageEffect effect) {
        super(effect);
    }

    @Override
    public ChandraAblazeDamageEffect copy() {
        return new ChandraAblazeDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = (Card) this.getValue("discardedCard");
        if (card != null && card.getColor(game).isRed()) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                permanent.damage(4, source.getSourceId(), source, game, false, true);
                return true;
            }

            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (player != null) {
                player.damage(4, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }
}

class ChandraAblazeCastCardsEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard();

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public ChandraAblazeCastCardsEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Cast any number of red instant and/or sorcery cards from your graveyard without paying their mana costs";
    }

    private ChandraAblazeCastCardsEffect(final ChandraAblazeCastCardsEffect effect) {
        super(effect);
    }

    @Override
    public ChandraAblazeCastCardsEffect copy() {
        return new ChandraAblazeCastCardsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Under this card's current oracle wording, it only casts red instant or sorcery cards
        // This may have been a mistake which could change in the future
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        CardUtil.castMultipleWithAttributeForFree(
                player, source, game,
                new CardsImpl(player.getGraveyard().getCards(filter, game)),
                StaticFilters.FILTER_CARD
        );
        return true;
    }
}
