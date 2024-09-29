package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.LifeCompareCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class VampireLacerator extends CardImpl {

    private static final Condition condition = new LifeCompareCondition(TargetController.OPPONENT, ComparisonType.MORE_THAN, 10);

    public VampireLacerator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ConditionalOneShotEffect(
                    new LoseLifeSourceControllerEffect(1), condition,
                    "you lose 1 life unless an opponent has 10 or less life"
        ), TargetController.YOU, false));
    }

    private VampireLacerator(final VampireLacerator card) {
        super(card);
    }

    @Override
    public VampireLacerator copy() {
        return new VampireLacerator(this);
    }
}
