package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DivineSmite extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public DivineSmite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature or planeswalker an opponent controls phases out. If that permanent is black, exile it instead.
        this.getSpellAbility().addEffect(new DivineSmiteEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private DivineSmite(final DivineSmite card) {
        super(card);
    }

    @Override
    public DivineSmite copy() {
        return new DivineSmite(this);
    }
}

class DivineSmiteEffect extends OneShotEffect {

    DivineSmiteEffect() {
        super(Outcome.Benefit);
        staticText = "target creature or planeswalker an opponent controls phases out. " +
                "If that permanent is black, exile it instead";
    }

    private DivineSmiteEffect(final DivineSmiteEffect effect) {
        super(effect);
    }

    @Override
    public DivineSmiteEffect copy() {
        return new DivineSmiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        if (!permanent.getColor(game).isBlack()) {
            permanent.phaseOut(game);
            return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.moveCards(permanent, Zone.EXILED, source, game);
    }
}
