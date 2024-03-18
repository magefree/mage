package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ItDoesntAddUp extends CardImpl {

    public ItDoesntAddUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}{B}");

        // Return target creature card from your graveyard to the battlefield. Suspect it.
        this.getSpellAbility().addEffect(new ItDoesntAddUpEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private ItDoesntAddUp(final ItDoesntAddUp card) {
        super(card);
    }

    @Override
    public ItDoesntAddUp copy() {
        return new ItDoesntAddUp(this);
    }
}

class ItDoesntAddUpEffect extends OneShotEffect {

    ItDoesntAddUpEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature card from your graveyard to the battlefield. Suspect it";
    }

    private ItDoesntAddUpEffect(final ItDoesntAddUpEffect effect) {
        super(effect);
    }

    @Override
    public ItDoesntAddUpEffect copy() {
        return new ItDoesntAddUpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Optional.ofNullable(game.getPermanent(card.getId()))
                .ifPresent(permanent -> permanent.setSuspected(true, game, source));
        return true;
    }
}
