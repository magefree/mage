
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author nantuko
 */
public final class MyrPropagator extends CardImpl {

    public MyrPropagator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.MYR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}, {tap}: Create a token that's a copy of Myr Propagator.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenCopySourceEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MyrPropagator(final MyrPropagator card) {
        super(card);
    }

    @Override
    public MyrPropagator copy() {
        return new MyrPropagator(this);
    }
}
