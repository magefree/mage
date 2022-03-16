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
import mage.constants.*;
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
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);

        this.setStartingLoyalty(5);

        // +1: Discard a card. If a red card is discarded this way, Chandra Ablaze deals 4 damage to any target.
        LoyaltyAbility ability = new LoyaltyAbility(new ChandraAblazeEffect1(), 1);
        ability.addEffect(new ChandraAblazeEffect2());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        // -2: Each player discards their hand, then draws three cards.
        ability = new LoyaltyAbility(new DiscardHandAllEffect(), -2);
        Effect effect = new DrawCardAllEffect(3);
        effect.setText(", then draws three cards");
        ability.addEffect(effect);
        this.addAbility(ability);
        // -7: Cast any number of red instant and/or sorcery cards from your graveyard without paying their mana costs.
        ability = new LoyaltyAbility(new ChandraAblazeEffect5(), -7);
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

class ChandraAblazeEffect1 extends OneShotEffect {

    public ChandraAblazeEffect1() {
        super(Outcome.Discard);
        this.staticText = "Discard a card";
    }

    public ChandraAblazeEffect1(final ChandraAblazeEffect1 effect) {
        super(effect);
    }

    @Override
    public ChandraAblazeEffect1 copy() {
        return new ChandraAblazeEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetDiscard target = new TargetDiscard(player.getId());
            player.choose(Outcome.Discard, target, source.getSourceId(), game);
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

class ChandraAblazeEffect2 extends OneShotEffect {

    public ChandraAblazeEffect2() {
        super(Outcome.Damage);
        this.staticText = "If a red card is discarded this way, {this} deals 4 damage to any target";
    }

    public ChandraAblazeEffect2(final ChandraAblazeEffect2 effect) {
        super(effect);
    }

    @Override
    public ChandraAblazeEffect2 copy() {
        return new ChandraAblazeEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = (Card) this.getValue("discardedCard");
        if (card != null && card.getColor(game).isRed()) {
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent != null) {
                permanent.damage(4, source.getSourceId(), source, game, false, true);
                return true;
            }

            Player player = game.getPlayer(targetPointer.getFirst(game, source));
            if (player != null) {
                player.damage(4, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }
}

class ChandraAblazeEffect5 extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard();

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public ChandraAblazeEffect5() {
        super(Outcome.PlayForFree);
        this.staticText = "Cast any number of red instant and/or sorcery cards from your graveyard without paying their mana costs";
    }

    public ChandraAblazeEffect5(final ChandraAblazeEffect5 effect) {
        super(effect);
    }

    @Override
    public ChandraAblazeEffect5 copy() {
        return new ChandraAblazeEffect5(this);
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
