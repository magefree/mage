
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.token.DevastatingSummonsElementalToken;

/**
 *
 * @author jeffwadsworth
 */
public final class DevastatingSummons extends CardImpl {

    public DevastatingSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // As an additional cost to cast Devastating Summons, sacrifice X lands.
        this.getSpellAbility().addCost(new SacrificeXTargetCost(new FilterControlledLandPermanent("lands"), true));

        // Create two X/X red Elemental creature tokens.
        this.getSpellAbility().addEffect(new DevastatingSummonsEffect());
    }

    private DevastatingSummons(final DevastatingSummons card) {
        super(card);
    }

    @Override
    public DevastatingSummons copy() {
        return new DevastatingSummons(this);
    }
}

class DevastatingSummonsEffect extends OneShotEffect {

    public DevastatingSummonsEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create two X/X red Elemental creature tokens";
    }

    public DevastatingSummonsEffect(final DevastatingSummonsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DevastatingSummonsElementalToken token = new DevastatingSummonsElementalToken();

        token.setPower(GetXValue.instance.calculate(game, source, this));
        token.setToughness(GetXValue.instance.calculate(game, source, this));

        token.putOntoBattlefield(2, game, source, source.getControllerId());

        return true;
    }

    @Override
    public DevastatingSummonsEffect copy() {
        return new DevastatingSummonsEffect(this);
    }

}
