
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Temba21
 */
public final class SkirkMarauder extends CardImpl {

    public SkirkMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Morph {2}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{R}")));
        
        // When Skirk Marauder is turned face up, it deals 2 damage to any target.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new DamageTargetEffect(2, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SkirkMarauder(final SkirkMarauder card) {
        super(card);
    }

    @Override
    public SkirkMarauder copy() {
        return new SkirkMarauder(this);
    }
}
