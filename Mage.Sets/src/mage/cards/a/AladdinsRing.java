
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
public final class AladdinsRing extends CardImpl {

    public AladdinsRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{8}");

        // {8}, {tap}: Aladdin's Ring deals 4 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(4), new ManaCostsImpl<>("{8}"));
        ability.addTarget(new TargetAnyTarget());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private AladdinsRing(final AladdinsRing card) {
        super(card);
    }

    @Override
    public AladdinsRing copy() {
        return new AladdinsRing(this);
    }
}
