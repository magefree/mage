
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class BarbarianRing extends CardImpl {

    public BarbarianRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {R}. Barbarian Ring deals 1 damage to you.
        Ability redManaAbility = new RedManaAbility();
        redManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(redManaAbility);

        // Threshold - {R}, {T}, Sacrifice Barbarian Ring: Barbarian Ring deals 2 damage to any target. Activate this ability only if seven or more cards are in your graveyard.
        Ability thresholdAbility = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
            new DamageTargetEffect(2),
            new ManaCostsImpl<>("{R}"),
            new CardsInControllerGraveyardCondition(7));
        thresholdAbility.addCost(new TapSourceCost());
        thresholdAbility.addCost(new SacrificeSourceCost());
        thresholdAbility.addTarget(new TargetAnyTarget());
        thresholdAbility.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(thresholdAbility);
    }

    private BarbarianRing(final BarbarianRing card) {
        super(card);
    }

    @Override
    public BarbarianRing copy() {
        return new BarbarianRing(this);
    }
}
