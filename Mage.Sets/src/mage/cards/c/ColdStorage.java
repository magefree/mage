
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnCreaturesFromExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class ColdStorage extends CardImpl {

    public ColdStorage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {3}: Exile target creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(this.getId(), this.getIdName()), new ManaCostsImpl("{3}"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
        // Sacrifice Cold Storage: Return each creature card exiled with Cold Storage to the battlefield under your control.
        ReturnCreaturesFromExileEffect returnFromExileEffect = new ReturnCreaturesFromExileEffect(this.getId(), false, "Return each creature card exiled with {this} to the battlefield under your control");
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, returnFromExileEffect,  new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private ColdStorage(final ColdStorage card) {
        super(card);
    }

    @Override
    public ColdStorage copy() {
        return new ColdStorage(this);
    }
}
