
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.XorLessLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class VampireLacerator extends CardImpl {

    public VampireLacerator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
            new ConditionalOneShotEffect(
                    new LoseLifeSourceControllerEffect(1),
                    new InvertCondition( new XorLessLifeCondition(XorLessLifeCondition.CheckType.AN_OPPONENT, 10) ),
                    "you lose 1 life unless an opponent has 10 or less life"), TargetController.YOU, false));
    }

    private VampireLacerator(final VampireLacerator card) {
        super(card);
    }

    @Override
    public VampireLacerator copy() {
        return new VampireLacerator(this);
    }
}
