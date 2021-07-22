package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CleansingWildfire extends CardImpl {

    public CleansingWildfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Destroy target land. Its controller may search their library for a basic land card, put it onto the battlefield tapped, then shuffle their library.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new CleansingWildfireEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private CleansingWildfire(final CleansingWildfire card) {
        super(card);
    }

    @Override
    public CleansingWildfire copy() {
        return new CleansingWildfire(this);
    }
}

class CleansingWildfireEffect extends OneShotEffect {

    CleansingWildfireEffect() {
        super(Outcome.Benefit);
        staticText = "Its controller may search their library for a basic land card, " +
                "put it onto the battlefield tapped, then shuffle.";
    }

    private CleansingWildfireEffect(final CleansingWildfireEffect effect) {
        super(effect);
    }

    @Override
    public CleansingWildfireEffect copy() {
        return new CleansingWildfireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent == null) {
            return false;
        }
        Player controller = game.getPlayer(permanent.getControllerId());
        if (controller == null) {
            return false;
        }
        if (!controller.chooseUse(Outcome.PutLandInPlay, "Search for a basic land and put it onto the battlefield tapped?", source, game)) {
            return true;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        if (controller.searchLibrary(target, source, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
