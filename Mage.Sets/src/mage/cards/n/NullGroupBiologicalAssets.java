package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class NullGroupBiologicalAssets extends CardImpl {

    public NullGroupBiologicalAssets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // During your turn, this creature has first strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
            MyTurnCondition.instance, "during your turn, {this} has first strike."
        )));

        // Whenever this creature attacks, you may discard a card. If you do, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
            new DrawCardSourceControllerEffect(1), new DiscardCardCost()
        ), false));
    }

    private NullGroupBiologicalAssets(final NullGroupBiologicalAssets card) {
        super(card);
    }

    @Override
    public NullGroupBiologicalAssets copy() {
        return new NullGroupBiologicalAssets(this);
    }
}
