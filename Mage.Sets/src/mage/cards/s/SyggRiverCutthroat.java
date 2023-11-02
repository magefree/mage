package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.OpponentLostLifeCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SyggRiverCutthroat extends CardImpl {

    public SyggRiverCutthroat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U/B}{U/B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // At the beginning of each end step, if an opponent lost 3 or more life this turn, you may draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                TargetController.ANY,
                new OpponentLostLifeCondition(ComparisonType.OR_GREATER, 3),
                true
        ).addHint(new ConditionHint(
                new OpponentLostLifeCondition(ComparisonType.OR_GREATER, 3),
                "An opponent lost 3 or more life this turn"
        )));
    }

    private SyggRiverCutthroat(final SyggRiverCutthroat card) {
        super(card);
    }

    @Override
    public SyggRiverCutthroat copy() {
        return new SyggRiverCutthroat(this);
    }
}
