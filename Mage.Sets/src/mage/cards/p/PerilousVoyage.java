package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PerilousVoyage extends CardImpl {

    private static final FilterNonlandPermanent filter
            = new FilterNonlandPermanent("nonland permanent you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public PerilousVoyage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nonland permanent you don't control to its owner's hand. If its converted mana cost was 2 or less, scry 2.
        this.getSpellAbility().addEffect(new PerilousVoyageEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private PerilousVoyage(final PerilousVoyage card) {
        super(card);
    }

    @Override
    public PerilousVoyage copy() {
        return new PerilousVoyage(this);
    }
}

class PerilousVoyageEffect extends OneShotEffect {

    PerilousVoyageEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target nonland permanent you don't control to its owner's hand. " +
                "If its mana value was 2 or less, scry 2";
    }

    private PerilousVoyageEffect(final PerilousVoyageEffect effect) {
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
        if (player == null || permanent == null) {
            return false;
        }
        boolean flag = permanent.getManaValue() <= 2;
        player.moveCards(permanent, Zone.HAND, source, game);
        if (flag) {
            player.scry(2, source, game);
        }
        return true;
    }
}
