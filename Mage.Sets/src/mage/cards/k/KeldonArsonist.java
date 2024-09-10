
package mage.cards.k;

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
import mage.filter.StaticFilters;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

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
        ability.addCost(new SacrificeTargetCost(2, StaticFilters.FILTER_LANDS));
        ability.addTarget(new TargetLandPermanent().withChooseHint("to destroy"));
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
