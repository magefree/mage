
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class KeldonArsonist extends CardImpl {

    public KeldonArsonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}, Sacrifice two lands: Destroy target land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, new FilterControlledLandPermanent("lands"), true)));
        TargetLandPermanent target = new TargetLandPermanent();
        target.setTargetName("land (to destroy)");
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private KeldonArsonist(final KeldonArsonist card) {
        super(card);
    }

    @Override
    public KeldonArsonist copy() {
        return new KeldonArsonist(this);
    }
}
