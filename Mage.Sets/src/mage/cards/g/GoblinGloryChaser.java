package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.RenownedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GoblinGloryChaser extends CardImpl {

    public GoblinGloryChaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Renown 1
        this.addAbility(new RenownAbility(1));

        // As long as Goblin Glory Chaser is renowned, it has menace.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new MenaceAbility(), Duration.WhileOnBattlefield),
                RenownedSourceCondition.THIS, "as long as {this} is renowned, " +
                "it has menace. <i>(It can't be blocked except by two or more creatures.)</i>"
        )));
    }

    private GoblinGloryChaser(final GoblinGloryChaser card) {
        super(card);
    }

    @Override
    public GoblinGloryChaser copy() {
        return new GoblinGloryChaser(this);
    }
}
