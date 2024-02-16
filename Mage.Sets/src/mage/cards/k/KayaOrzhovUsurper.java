package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInASingleGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KayaOrzhovUsurper extends CardImpl {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("nonland permanent with mana value 1 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public KayaOrzhovUsurper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KAYA);
        this.setStartingLoyalty(3);

        // +1: Exile up to two target cards from a single graveyard. You gain 2 life if at least one creature card was exiled this way.
        Ability ability = new LoyaltyAbility(new KayaOrzhovUsurperExileEffect(), 1);
        ability.addTarget(new TargetCardInASingleGraveyard(0, 2, StaticFilters.FILTER_CARD));
        this.addAbility(ability);

        // -1: Exile target nonland permanent with converted mana cost 1 or less.
        ability = new LoyaltyAbility(new ExileTargetEffect(), -1);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // -5: Kaya, Orzhov Usurper deals damage to target player equal to the number of cards that player owns in exile and you gain that much life.
        ability = new LoyaltyAbility(new KayaOrzhovUsurperDamageEffect(), -5);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private KayaOrzhovUsurper(final KayaOrzhovUsurper card) {
        super(card);
    }

    @Override
    public KayaOrzhovUsurper copy() {
        return new KayaOrzhovUsurper(this);
    }
}

class KayaOrzhovUsurperExileEffect extends OneShotEffect {

    KayaOrzhovUsurperExileEffect() {
        super(Outcome.Benefit);
        staticText = "Exile up to two target cards from a single graveyard. " +
                "You gain 2 life if at least one creature card was exiled this way.";
    }

    private KayaOrzhovUsurperExileEffect(final KayaOrzhovUsurperExileEffect effect) {
        super(effect);
    }

    @Override
    public KayaOrzhovUsurperExileEffect copy() {
        return new KayaOrzhovUsurperExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (Target target : source.getTargets()) {
            for (UUID targetId : target.getTargets()) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    cards.add(card);
                }
            }
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards.getCards(game)) {
            if (card != null && card.isCreature(game)) {
                player.gainLife(2, game, source);
                break;
            }
        }
        return false;
    }
}

class KayaOrzhovUsurperDamageEffect extends OneShotEffect {

    KayaOrzhovUsurperDamageEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals damage to target player equal to the number of cards " +
                "that player owns in exile and you gain that much life.";
    }

    private KayaOrzhovUsurperDamageEffect(final KayaOrzhovUsurperDamageEffect effect) {
        super(effect);
    }

    @Override
    public KayaOrzhovUsurperDamageEffect copy() {
        return new KayaOrzhovUsurperDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        int count = 0;
        for (Card card : game.getExile().getAllCards(game)) {
            if (card != null && card.getOwnerId().equals(player.getId())) {
                count += 1;
            }
        }
        player.damage(count, source.getSourceId(), source, game);
        controller.gainLife(count, game, source);
        return true;
    }
}