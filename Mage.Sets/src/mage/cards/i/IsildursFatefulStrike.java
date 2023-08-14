package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.LegendarySpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IsildursFatefulStrike extends CardImpl {

    public IsildursFatefulStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // (You may cast a legendary instant only if you control a legendary creature or planeswalker.)
        this.addAbility(new LegendarySpellAbility(true));

        // Destroy target creature. If its controller has more than four cards in hand, they exile cards from their hand equal to the difference.
        this.getSpellAbility().addEffect(new IsildursFatefulStrikeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private IsildursFatefulStrike(final IsildursFatefulStrike card) {
        super(card);
    }

    @Override
    public IsildursFatefulStrike copy() {
        return new IsildursFatefulStrike(this);
    }
}

class IsildursFatefulStrikeEffect extends OneShotEffect {

    IsildursFatefulStrikeEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target creature. If its controller has more than four cards in hand, " +
                "they exile cards from their hand equal to the difference";
    }

    private IsildursFatefulStrikeEffect(final IsildursFatefulStrikeEffect effect) {
        super(effect);
    }

    @Override
    public IsildursFatefulStrikeEffect copy() {
        return new IsildursFatefulStrikeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        permanent.destroy(source, game);
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null || player.getHand().size() <= 4) {
            return true;
        }
        TargetCard target = new TargetCardInHand(player.getHand().size() - 4, StaticFilters.FILTER_CARD);
        player.choose(outcome, player.getHand(), target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
        return true;
    }
}
