package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheKeyToTheVault extends CardImpl {

    public TheKeyToTheVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature deals combat damage to a player, look at that many cards from the top of your library. You may exile a nonland card from among them. Put the rest on the bottom of your library in a random order. You may cast the exiled card without paying its mana cost.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new TheKeyToTheVaultEffect(), "equipped",
                false, false, true
        ));

        // Equip {2}{U}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new ManaCostsImpl<>("{2}{U}"), false));
    }

    private TheKeyToTheVault(final TheKeyToTheVault card) {
        super(card);
    }

    @Override
    public TheKeyToTheVault copy() {
        return new TheKeyToTheVault(this);
    }
}

class TheKeyToTheVaultEffect extends OneShotEffect {

    TheKeyToTheVaultEffect() {
        super(Outcome.Benefit);
        staticText = "look at that many cards from the top of your library. " +
                "You may exile a nonland card from among them. " +
                "Put the rest on the bottom of your library in a random order. " +
                "You may cast the exiled card without paying its mana cost";
    }

    private TheKeyToTheVaultEffect(final TheKeyToTheVaultEffect effect) {
        super(effect);
    }

    @Override
    public TheKeyToTheVaultEffect copy() {
        return new TheKeyToTheVaultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int damage = (Integer) getValue("damage");
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, damage));
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_NON_LAND);
        player.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.EXILED, source, game);
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        CardUtil.castSpellWithAttributesForFree(player, source, game, card);
        return true;
    }
}
