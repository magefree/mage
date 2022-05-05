package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author Loki
 */
public final class FaithsReward extends CardImpl {

    public FaithsReward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Return to the battlefield all permanent cards in your graveyard that were put there from the battlefield this turn.
        this.getSpellAbility().addEffect(new FaithsRewardEffect());
        this.getSpellAbility().addWatcher(new CardsPutIntoGraveyardWatcher());
    }

    private FaithsReward(final FaithsReward card) {
        super(card);
    }

    @Override
    public FaithsReward copy() {
        return new FaithsReward(this);
    }
}

class FaithsRewardEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterPermanentCard();

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    FaithsRewardEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Return to the battlefield all permanent cards in your graveyard that were put there from the battlefield this turn";
    }

    private FaithsRewardEffect(final FaithsRewardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return player.moveCards(player.getGraveyard().getCards(
                filter, source.getControllerId(), source, game
        ), Zone.BATTLEFIELD, source, game);
    }

    @Override
    public FaithsRewardEffect copy() {
        return new FaithsRewardEffect(this);
    }
}
