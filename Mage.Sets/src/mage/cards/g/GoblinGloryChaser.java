
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.RenownedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class GoblinGloryChaser extends CardImpl {

    public GoblinGloryChaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Renown 1
        this.addAbility(new RenownAbility(1));

        // As long as Goblin Glory Chaser is renowned, it has menace.
        Effect effect = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new MenaceAbility(), Duration.WhileOnBattlefield),
                RenownedSourceCondition.instance,
                "As long as {this} is renowned, it has menace. " +
                        "<i>(It can't be blocked except by two or more creatures.)</i>");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        this.addAbility(ability);
    }

    private GoblinGloryChaser(final GoblinGloryChaser card) {
        super(card);
    }

    @Override
    public GoblinGloryChaser copy() {
        return new GoblinGloryChaser(this);
    }
}
