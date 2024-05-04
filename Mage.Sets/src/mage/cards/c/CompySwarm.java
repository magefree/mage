package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jimga150
 */
public final class CompySwarm extends CardImpl {

    public CompySwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, if a creature died this turn, create a tapped token that's a copy of Compy Swarm.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfYourEndStepTriggeredAbility(new CreateTokenCopySourceEffect(1, true), false),
                MorbidCondition.instance,
                "At the beginning of your end step, if a creature died this turn, create a tapped token that's a copy of {this}."
        ).addHint(MorbidHint.instance));
    }

    private CompySwarm(final CompySwarm card) {
        super(card);
    }

    @Override
    public CompySwarm copy() {
        return new CompySwarm(this);
    }
}
