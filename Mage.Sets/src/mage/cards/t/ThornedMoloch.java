
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author spjspj
 */
public final class ThornedMoloch extends CardImpl {

    public ThornedMoloch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Thorned Moloch has first strike as long as it's attacking.
        this.addAbility(
                new SimpleStaticAbility(
                        Zone.BATTLEFIELD,
                        new ConditionalContinuousEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()), SourceAttackingCondition.instance, "{this} has first strike as long as it's attacking")
                )
        );

    }

    private ThornedMoloch(final ThornedMoloch card) {
        super(card);
    }

    @Override
    public ThornedMoloch copy() {
        return new ThornedMoloch(this);
    }
}
