package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.KarnLivingLegacyEmblem;
import mage.game.permanent.token.PowerstoneToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KarnLivingLegacy extends CardImpl {

    public KarnLivingLegacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KARN);
        this.setStartingLoyalty(4);

        // +1: Create a tapped Powerstone token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(
                new PowerstoneToken(), 1, true, false
        ), 1));

        // -1: Pay any amount of mana. Look at that many cards from the top of your library, then put one of those cards into your hand and rest on the bottom of your library in a random order.
        this.addAbility(new LoyaltyAbility(new KarnLivingLegacyEffect(), -1));

        // -7: You get an emblem with "Tap an untapped artifact you control: This emblem deals 1 damage to any target."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new KarnLivingLegacyEmblem()), -7));
    }

    private KarnLivingLegacy(final KarnLivingLegacy card) {
        super(card);
    }

    @Override
    public KarnLivingLegacy copy() {
        return new KarnLivingLegacy(this);
    }
}

class KarnLivingLegacyEffect extends OneShotEffect {

    KarnLivingLegacyEffect() {
        super(Outcome.Benefit);
        staticText = "pay any amount of mana. Look at that many cards from the top of your library, " +
                "then put one of those cards into your hand and the rest on the bottom of your library in a random order";
    }

    private KarnLivingLegacyEffect(final KarnLivingLegacyEffect effect) {
        super(effect);
    }

    @Override
    public KarnLivingLegacyEffect copy() {
        return new KarnLivingLegacyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getLibrary().size() < 1) {
            return false;
        }
        int amount = ManaUtil.playerPaysXGenericMana(
                false, "Karn, Living Legacy", player, source, game
        );
        if (amount < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, amount));
        if (cards.isEmpty()) {
            return true;
        }
        TargetCard target = new TargetCardInLibrary(StaticFilters.FILTER_CARD);
        player.choose(outcome, cards, target, source, game);
        Card card = cards.get(target.getFirstTarget(), game);
        if (card != null) {
            player.moveCards(card, Zone.HAND, source, game);
        }
        cards.retainZone(Zone.LIBRARY, game);
        cards.remove(card);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
