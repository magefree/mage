
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Backfir3
 */
public final class BarrinMasterWizard extends CardImpl {

    public BarrinMasterWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //{2}, Sacrifice a permanent: Return target creature to its owner's hand.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledPermanent())));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BarrinMasterWizard(final BarrinMasterWizard card) {
        super(card);
    }

    @Override
    public BarrinMasterWizard copy() {
        return new BarrinMasterWizard(this);
    }
}