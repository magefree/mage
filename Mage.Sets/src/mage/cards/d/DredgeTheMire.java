package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
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
public final class DredgeTheMire extends CardImpl {

    public DredgeTheMire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Each opponent chooses a creature card in their graveyard. Put those cards onto the battlefield under your control.
        this.getSpellAbility().addEffect(new DredgeTheMireEffect());
    }

    private DredgeTheMire(final DredgeTheMire card) {
        super(card);
    }

    @Override
    public DredgeTheMire copy() {
        return new DredgeTheMire(this);
    }
}

class DredgeTheMireEffect extends OneShotEffect {

    DredgeTheMireEffect() {
        super(Outcome.Benefit);
        staticText = "Each opponent chooses a creature card in their graveyard. " +
                "Put those cards onto the battlefield under your control.";
    }

    private DredgeTheMireEffect(final DredgeTheMireEffect effect) {
        super(effect);
    }

    @Override
    public DredgeTheMireEffect copy() {
        return new DredgeTheMireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player == null || player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) == 0) {
                continue;
            }
            TargetCard target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
            target.setNotTarget(true);
            if (!player.choose(outcome, player.getGraveyard(), target, source, game)) {
                continue;
            }
            cards.add(target.getFirstTarget());
        }
        return controller.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}
