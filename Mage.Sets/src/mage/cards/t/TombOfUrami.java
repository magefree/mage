
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.UramiToken;

/**
 *
 * @author Plopman
 */
public final class TombOfUrami extends CardImpl {

    public TombOfUrami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.addSuperType(SuperType.LEGENDARY);

        // {tap}: Add {B}. Tomb of Urami deals 1 damage to you if you don't control an Ogre.
        Ability ability = new BlackManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
        // {2}{B}{B}, {tap}, Sacrifice all lands you control: Create a legendary 5/5 black Demon Spirit creature token with flying named Urami.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new UramiToken()), new ManaCostsImpl<>("{2}{B}{B}"));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new SacrificeAllLandCost());
        this.addAbility(ability2);
    }

    private TombOfUrami(final TombOfUrami card) {
        super(card);
    }

    @Override
    public TombOfUrami copy() {
        return new TombOfUrami(this);
    }
}

class SacrificeAllLandCost extends CostImpl {

    public SacrificeAllLandCost() {
        this.text = "Sacrifice all lands you control";
    }

    public SacrificeAllLandCost(SacrificeAllLandCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledLandPermanent(), ability.getControllerId(), game)) {
            paid |= permanent.sacrifice(source, game);
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledLandPermanent(), ability.getControllerId(), game)) {
            if (!game.getPlayer(controllerId).canPaySacrificeCost(permanent, source, controllerId, game)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public SacrificeAllLandCost copy() {
        return new SacrificeAllLandCost(this);
    }

}
