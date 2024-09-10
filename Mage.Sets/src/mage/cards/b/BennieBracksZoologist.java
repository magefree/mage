package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.CreatedTokenThisTurnCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.watchers.common.CreatedTokenWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BennieBracksZoologist extends CardImpl {

    public BennieBracksZoologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // At the beginning of each end step, if you created a token this turn, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                TargetController.ANY, CreatedTokenThisTurnCondition.instance, false
        ).addHint(CreatedTokenThisTurnCondition.getHint()), new CreatedTokenWatcher());
    }

    private BennieBracksZoologist(final BennieBracksZoologist card) {
        super(card);
    }

    @Override
    public BennieBracksZoologist copy() {
        return new BennieBracksZoologist(this);
    }
}
