
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetOpponent;

/**
 *
 * @author MarcoMarin
 */
public final class DwarvenCatapult extends CardImpl {

    public DwarvenCatapult(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{R}");

        // Dwarven Catapult deals X damage divided evenly, rounded down, among all creatures target opponent controls.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new DwarvenCatapultEffect());
    }

    private DwarvenCatapult(final DwarvenCatapult card) {
        super(card);
    }

    @Override
    public DwarvenCatapult copy() {
        return new DwarvenCatapult(this);
    }
}


class DwarvenCatapultEffect extends OneShotEffect {

    public DwarvenCatapultEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage divided evenly, rounded down, among all creatures target opponent controls.";
    }

    private DwarvenCatapultEffect(final DwarvenCatapultEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int howMany = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, source.getFirstTarget(), game).size();
        int amount = source.getManaCostsToPay().getX()/howMany;
        
        DamageAllControlledTargetEffect dmgEffect = new DamageAllControlledTargetEffect(amount, new FilterCreaturePermanent());
        return dmgEffect.apply(game, source);
    }

    @Override
    public DwarvenCatapultEffect copy() {
        return new DwarvenCatapultEffect(this);
    }

}