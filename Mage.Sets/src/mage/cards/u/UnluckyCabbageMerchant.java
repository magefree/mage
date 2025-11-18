package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.FoodToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnluckyCabbageMerchant extends CardImpl {

    public UnluckyCabbageMerchant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Whenever you sacrifice a Food, you may search your library for a basic land card and put it onto the battlefield tapped. If you search your library this way, put this creature on the bottom of its owner's library, then shuffle.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new UnluckyCabbageMerchantEffect(), StaticFilters.FILTER_CONTROLLED_FOOD
        ));
    }

    private UnluckyCabbageMerchant(final UnluckyCabbageMerchant card) {
        super(card);
    }

    @Override
    public UnluckyCabbageMerchant copy() {
        return new UnluckyCabbageMerchant(this);
    }
}

class UnluckyCabbageMerchantEffect extends OneShotEffect {

    UnluckyCabbageMerchantEffect() {
        super(Outcome.Benefit);
        staticText = "you may search your library for a basic land card and put it onto the battlefield tapped. " +
                "If you search your library this way, put this creature on the bottom of its owner's library, then shuffle";
    }

    private UnluckyCabbageMerchantEffect(final UnluckyCabbageMerchantEffect effect) {
        super(effect);
    }

    @Override
    public UnluckyCabbageMerchantEffect copy() {
        return new UnluckyCabbageMerchantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(
                Outcome.PutLandInPlay, "Search your library for a basic land?", source, game
        )) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        if (!player.searchLibrary(target, source, game)) {
            return false;
        }
        player.moveCards(
                player.getLibrary().getCard(target.getFirstTarget(), game), Zone.BATTLEFIELD,
                source, game, true, false, false, null
        );
        player.putCardsOnBottomOfLibrary(source.getSourcePermanentIfItStillExists(game), game, source);
        player.shuffleLibrary(source, game);
        return true;
    }
}
