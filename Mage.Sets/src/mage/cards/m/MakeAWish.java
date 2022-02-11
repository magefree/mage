package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author North
 */
public final class MakeAWish extends CardImpl {

    public MakeAWish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Return two cards at random from your graveyard to your hand.
        this.getSpellAbility().addEffect(new MakeAWishEffect());
    }

    private MakeAWish(final MakeAWish card) {
        super(card);
    }

    @Override
    public MakeAWish copy() {
        return new MakeAWish(this);
    }
}

class MakeAWishEffect extends OneShotEffect {

    MakeAWishEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return two cards at random from your graveyard to your hand";
    }

    private MakeAWishEffect(final MakeAWishEffect effect) {
        super(effect);
    }

    @Override
    public MakeAWishEffect copy() {
        return new MakeAWishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(Math.min(player.getGraveyard().size(), 2), StaticFilters.FILTER_CARD);
        target.setNotTarget(true);
        target.setRandom(true);
        target.chooseTarget(outcome, player.getId(), source, game);
        return player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
    }
}
