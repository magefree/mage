package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.*;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 *
 * @author weirddan455
 */
public final class MarkovPurifier extends CardImpl {

    public MarkovPurifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your end step, if you gained life this turn, you may pay {2}. If you do, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD,
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new GenericManaCost(2)),
                TargetController.YOU, new YouGainedLifeCondition(ComparisonType.MORE_THAN, 0), false
        ), new PlayerGainedLifeWatcher());
    }

    private MarkovPurifier(final MarkovPurifier card) {
        super(card);
    }

    @Override
    public MarkovPurifier copy() {
        return new MarkovPurifier(this);
    }
}
