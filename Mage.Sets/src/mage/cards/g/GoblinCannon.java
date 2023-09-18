
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
public final class GoblinCannon extends CardImpl {

    public GoblinCannon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {2}: Goblin Cannon deals 1 damage to any target. Sacrifice Goblin Cannon.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{2}"));
        ability.addTarget(new TargetAnyTarget());
        ability.addEffect(new SacrificeSourceEffect());
        this.addAbility(ability);
    }

    private GoblinCannon(final GoblinCannon card) {
        super(card);
    }

    @Override
    public GoblinCannon copy() {
        return new GoblinCannon(this);
    }
}
