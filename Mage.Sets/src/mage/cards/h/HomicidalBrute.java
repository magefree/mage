
package mage.cards.h;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.AttackedThisTurnSourceCondition;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class HomicidalBrute extends CardImpl {

    private static final Condition condition = new InvertCondition(
            AttackedThisTurnSourceCondition.instance, "{this} didn't attack this turn"
    );

    public HomicidalBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MUTANT);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.color.setRed(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // At the beginning of your end step, if Homicidal Brute didn't attack this turn, tap Homicidal Brute, then transform it.
        TriggeredAbility ability = new BeginningOfEndStepTriggeredAbility(new TapSourceEffect());
        ability.addEffect(new TransformSourceEffect().setText(", then transform it"));
        this.addAbility(ability.withInterveningIf(condition));
    }

    private HomicidalBrute(final HomicidalBrute card) {
        super(card);
    }

    @Override
    public HomicidalBrute copy() {
        return new HomicidalBrute(this);
    }

}
