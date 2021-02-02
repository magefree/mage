
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.MonstrosityAbility;
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
public final class SkitteringCrustacean extends CardImpl {

    public SkitteringCrustacean(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {6}{U}: Monstrosity 4.
        this.addAbility(new MonstrosityAbility("{6}{U}", 4));

        // As long as Skittering Crustacean is monstrous, it has hexproof.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield),
                        MonstrousCondition.instance,
                        "As long as {this} is monstrous, it has hexproof"));
        this.addAbility(ability);
    }

    private SkitteringCrustacean(final SkitteringCrustacean card) {
        super(card);
    }

    @Override
    public SkitteringCrustacean copy() {
        return new SkitteringCrustacean(this);
    }
}
