package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ExtractFromDarkness extends CardImpl {

    public ExtractFromDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{B}");

        // Each player puts the top two cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new MillCardsEachPlayerEffect(2, TargetController.ANY));

        // Then put a creature card from a graveyard onto the battlefield under your control.
        this.getSpellAbility().addEffect(new ExtractFromDarknessEffect());
    }

    private ExtractFromDarkness(final ExtractFromDarkness card) {
        super(card);
    }

    @Override
    public ExtractFromDarkness copy() {
        return new ExtractFromDarkness(this);
    }
}

class ExtractFromDarknessEffect extends OneShotEffect {

    ExtractFromDarknessEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Then you put a creature card from a graveyard onto the battlefield under your control";
    }

    private ExtractFromDarknessEffect(final ExtractFromDarknessEffect effect) {
        super(effect);
    }

    @Override
    public ExtractFromDarknessEffect copy() {
        return new ExtractFromDarknessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE);
            target.setNotTarget(true);
            if (target.canChoose(source.getControllerId(), source, game)
                    && controller.chooseTarget(outcome, target, source, game)) {
                return controller.moveCards(game.getCard(target.getFirstTarget()), Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
