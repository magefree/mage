package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class UnexpectedlyAbsent extends CardImpl {

    public UnexpectedlyAbsent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}{W}");

        // Put target nonland permanent into its owner's library just beneath the top X cards of that library.
        this.getSpellAbility().addEffect(new UnexpectedlyAbsentEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_NON_LAND));

    }

    private UnexpectedlyAbsent(final UnexpectedlyAbsent card) {
        super(card);
    }

    @Override
    public UnexpectedlyAbsent copy() {
        return new UnexpectedlyAbsent(this);
    }
}

class UnexpectedlyAbsentEffect extends OneShotEffect {

    public UnexpectedlyAbsentEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target nonland permanent into its owner's library just beneath the top X cards of that library";
    }

    private UnexpectedlyAbsentEffect(final UnexpectedlyAbsentEffect effect) {
        super(effect);
    }

    @Override
    public UnexpectedlyAbsentEffect copy() {
        return new UnexpectedlyAbsentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                controller.putCardOnTopXOfLibrary(permanent, game, source, source.getManaCostsToPay().getX() + 1, true);
                return true;
            }
        }

        return false;
    }
}
