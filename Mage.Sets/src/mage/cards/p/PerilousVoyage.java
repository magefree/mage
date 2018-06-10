
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class PerilousVoyage extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public PerilousVoyage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nonland permanent you don't control to its owner's hand. If its converted mana cost was 2 or less, scry 2.
        this.getSpellAbility().addEffect(new PerilousVoyageEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public PerilousVoyage(final PerilousVoyage card) {
        super(card);
    }

    @Override
    public PerilousVoyage copy() {
        return new PerilousVoyage(this);
    }
}

class PerilousVoyageEffect extends OneShotEffect {

    PerilousVoyageEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return target nonland permanent you don't control to its owner's hand. If its converted mana cost was 2 or less, scry 2";
    }

    PerilousVoyageEffect(final PerilousVoyageEffect effect) {
        super(effect);
    }

    @Override
    public PerilousVoyageEffect copy() {
        return new PerilousVoyageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            boolean isLittle = permanent.getConvertedManaCost() < 3;
            permanent.moveToZone(Zone.HAND, source.getSourceId(), game, true);
            if (isLittle && player != null) {
                player.scry(2, source, game);
            }
        }
        return false;
    }
}
