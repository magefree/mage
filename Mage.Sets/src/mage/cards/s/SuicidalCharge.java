
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class SuicidalCharge extends CardImpl {

    public SuicidalCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{R}");


        // Sacrifice Suicidal Charge: Creatures your opponents control get -1/-1 until end of turn. Those creatures attack this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostOpponentsEffect(-1, -1, Duration.EndOfTurn), new SacrificeSourceCost());
        ability.addEffect(new SuicidalChargeEffect());
        this.addAbility(ability);
        
    }

    private SuicidalCharge(final SuicidalCharge card) {
        super(card);
    }

    @Override
    public SuicidalCharge copy() {
        return new SuicidalCharge(this);
    }
}

class SuicidalChargeEffect extends RequirementEffect {

    public SuicidalChargeEffect() {
        super(Duration.EndOfTurn);
        staticText = "Those creatures attack this turn if able";
    }

    private SuicidalChargeEffect(final SuicidalChargeEffect effect) {
        super(effect);
    }

    @Override
    public SuicidalChargeEffect copy() {
        return new SuicidalChargeEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(permanent.getControllerId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

}