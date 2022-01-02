package mage.cards.r;

import java.util.UUID;
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
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class RenewingTouch extends CardImpl {

    public RenewingTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Shuffle any number of target creature cards from your graveyard into your library.
        this.getSpellAbility().addEffect(new RenewingTouchEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
    }

    private RenewingTouch(final RenewingTouch card) {
        super(card);
    }

    @Override
    public RenewingTouch copy() {
        return new RenewingTouch(this);
    }
}

class RenewingTouchEffect extends OneShotEffect {

    RenewingTouchEffect() {
        super(Outcome.Neutral);
        this.staticText = "Shuffle any number of target creature cards from your graveyard into your library";
    }

    RenewingTouchEffect(final RenewingTouchEffect effect) {
        super(effect);
    }

    @Override
    public RenewingTouchEffect copy() {
        return new RenewingTouchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.moveCards(new CardsImpl(this.getTargetPointer().getTargets(game, source)), Zone.LIBRARY, source, game);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
