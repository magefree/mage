
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class DomesticatedHydra extends CardImpl {

    public DomesticatedHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {X}{G}{G}{G}: Monstrosity X.
        this.addAbility(new MonstrosityAbility("{X}{G}{G}{G}", Integer.MAX_VALUE));

        // As long as Domesticated Hydra is monstrous, it has trample.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield),
                        MonstrousCondition.instance,
                        "As long as {this} is monstrous, it has trample"));
        this.addAbility(ability);
    }

    private DomesticatedHydra(final DomesticatedHydra card) {
        super(card);
    }

    @Override
    public DomesticatedHydra copy() {
        return new DomesticatedHydra(this);
    }
}
