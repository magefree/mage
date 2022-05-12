
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class BloodhallPriest extends CardImpl {

    public BloodhallPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        this.subtype.add(SubType.VAMPIRE, SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Bloodhall Priest enters the battlefield or attacks, if you have no cards in hand, Bloodhall Priest deals 2 damage to any target.
        TriggeredAbility triggeredAbility = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DamageTargetEffect(2));
        triggeredAbility.addTarget(new TargetAnyTarget());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                triggeredAbility,
                HellbentCondition.instance,
                "Whenever {this} enters the battlefield or attacks, if you have no cards in hand, {this} deals 2 damage to any target"
        ));

        // Madness {1}{B}{R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{1}{B}{R}")));
    }

    private BloodhallPriest(final BloodhallPriest card) {
        super(card);
    }

    @Override
    public BloodhallPriest copy() {
        return new BloodhallPriest(this);
    }
}
