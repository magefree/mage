
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public final class PhyrexiasCore extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public PhyrexiasCore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new ColorlessManaAbility());
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), new TapSourceCost());
        ability.addCost(new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private PhyrexiasCore(final PhyrexiasCore card) {
        super(card);
    }

    @Override
    public PhyrexiasCore copy() {
        return new PhyrexiasCore(this);
    }
}
