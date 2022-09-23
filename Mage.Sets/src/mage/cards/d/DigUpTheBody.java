package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
 * @author TheElk801
 */
public final class DigUpTheBody extends CardImpl {

    public DigUpTheBody(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Casualty 1
        this.addAbility(new CasualtyAbility(1));

        // Mill two cards, then return a creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new DigUpTheBodyEffect());
    }

    private DigUpTheBody(final DigUpTheBody card) {
        super(card);
    }

    @Override
    public DigUpTheBody copy() {
        return new DigUpTheBody(this);
    }
}

class DigUpTheBodyEffect extends OneShotEffect {

    DigUpTheBodyEffect() {
        super(Outcome.Benefit);
        staticText = "mill two cards, then you may return a creature card from your graveyard to your hand";
    }

    private DigUpTheBodyEffect(final DigUpTheBodyEffect effect) {
        super(effect);
    }

    @Override
    public DigUpTheBodyEffect copy() {
        return new DigUpTheBodyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.millCards(2, source, game);
        TargetCard target = new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        target.setNotTarget(true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return true;
        }
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }
}
