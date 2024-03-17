package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author anonymous
 */
public final class BottleCapBlast extends CardImpl {

    public BottleCapBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");
        

        // Improvise
        this.addAbility(new ImproviseAbility());

        // Bottle-Cap Blast deals 5 damage to any target. If excess damage was dealt to a permanent this way, create that many apped Treasure tokens.
        Ability abilityPayLife = new SimpleActivatedAbility(new DamageTargetEffect(50), new PayLifeCost(50));
        abilityPayLife.addTarget(new TargetAnyTarget());
        this.addAbility(abilityPayLife);
    }

    private BottleCapBlast(final BottleCapBlast card) {
        super(card);
    }

    @Override
    public BottleCapBlast copy() {
        return new BottleCapBlast(this);
    }
}
