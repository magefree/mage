package mage.cards.i;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class IvoryCraneNetsuke extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.MORE_THAN, 6);

    public IvoryCraneNetsuke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // At the beginning of your upkeep, if you have seven or more cards in hand, you gain 4 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(4)).withInterveningIf(condition));
    }

    private IvoryCraneNetsuke(final IvoryCraneNetsuke card) {
        super(card);
    }

    @Override
    public IvoryCraneNetsuke copy() {
        return new IvoryCraneNetsuke(this);
    }
}
