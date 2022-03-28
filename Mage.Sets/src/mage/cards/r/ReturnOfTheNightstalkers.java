
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class ReturnOfTheNightstalkers extends CardImpl {

    public ReturnOfTheNightstalkers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        // Return all Nightstalker permanent cards from your graveyard to the battlefield. Then destroy all Swamps you control.
        this.getSpellAbility().addEffect(new ReturnOfTheNightstalkersEffect());
    }

    private ReturnOfTheNightstalkers(final ReturnOfTheNightstalkers card) {
        super(card);
    }

    @Override
    public ReturnOfTheNightstalkers copy() {
        return new ReturnOfTheNightstalkers(this);
    }
}

class ReturnOfTheNightstalkersEffect extends OneShotEffect {

    private static final FilterPermanentCard filter1 = new FilterPermanentCard();
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent();

    static {
        filter1.add(SubType.NIGHTSTALKER.getPredicate());
        filter2.add(SubType.SWAMP.getPredicate());
    }

    public ReturnOfTheNightstalkersEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Return all Nightstalker permanent cards from your graveyard to the battlefield. Then destroy all Swamps you control";
    }

    public ReturnOfTheNightstalkersEffect(final ReturnOfTheNightstalkersEffect effect) {
        super(effect);
    }

    @Override
    public ReturnOfTheNightstalkersEffect copy() {
        return new ReturnOfTheNightstalkersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.moveCards(controller.getGraveyard().getCards(filter1, game), Zone.BATTLEFIELD, source, game);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter2, source.getControllerId(), source, game)) {
                permanent.destroy(source, game, false);
            }
            return true;
        }
        return false;
    }
}
