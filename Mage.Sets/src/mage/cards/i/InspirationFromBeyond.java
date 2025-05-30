package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InspirationFromBeyond extends CardImpl {

    public InspirationFromBeyond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Mill three cards, then return an instant or sorcery card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new MillCardsControllerEffect(3));
        this.getSpellAbility().addEffect(new InspirationFromBeyondEffect());

        // Flashback {5}{U}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{U}{U}")));
    }

    private InspirationFromBeyond(final InspirationFromBeyond card) {
        super(card);
    }

    @Override
    public InspirationFromBeyond copy() {
        return new InspirationFromBeyond(this);
    }
}

class InspirationFromBeyondEffect extends OneShotEffect {

    InspirationFromBeyondEffect() {
        super(Outcome.Benefit);
        staticText = ", then return an instant or sorcery card from your graveyard to your hand";
    }

    private InspirationFromBeyondEffect(final InspirationFromBeyondEffect effect) {
        super(effect);
    }

    @Override
    public InspirationFromBeyondEffect copy() {
        return new InspirationFromBeyondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card;
        Cards cards = new CardsImpl(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game));
        switch (cards.size()) {
            case 0:
                return false;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCardInGraveyard();
                target.withNotTarget(true);
                player.choose(Outcome.ReturnToHand, cards, target, source, game);
                card = cards.get(target.getFirstTarget(), game);
        }
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }
}
