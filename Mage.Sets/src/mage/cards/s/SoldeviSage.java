
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardOneOfThemEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Quercitron
 */
public final class SoldeviSage extends CardImpl {

    public SoldeviSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}, Sacrifice two lands: Draw three cards, then discard one of them.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardOneOfThemEffect(3), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, new FilterControlledLandPermanent("lands"), true)));
        this.addAbility(ability);
    }

    private SoldeviSage(final SoldeviSage card) {
        super(card);
    }

    @Override
    public SoldeviSage copy() {
        return new SoldeviSage(this);
    }
}