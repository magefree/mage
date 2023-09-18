
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
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
public final class SinuousVermin extends CardImpl {

    public SinuousVermin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}{B}{B}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{3}{B}{B}", 3));

        // As long as Sinuous Vermin is monstrous, it has menace.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(new MenaceAbility(), Duration.WhileOnBattlefield),
                        MonstrousCondition.instance,
                        "As long as {this} is monstrous, it has menace. " +
                                "<i>(It can't be blocked except by two or more creatures.)</i>"));
        this.addAbility(ability);
    }

    private SinuousVermin(final SinuousVermin card) {
        super(card);
    }

    @Override
    public SinuousVermin copy() {
        return new SinuousVermin(this);
    }
}
