package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.ControlsPermanentGreatestCMCCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PadeemConsulOfInnovation extends CardImpl {

    private static final Condition condition
            = new ControlsPermanentGreatestCMCCondition(StaticFilters.FILTER_PERMANENT_ARTIFACT);

    public PadeemConsulOfInnovation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Artifacts you control have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ARTIFACTS, false
        )));

        // At the beginning of your upkeep, if you control the artifact with the highest converted mana cost or tied for the highest converted mana cost, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), TargetController.YOU, false
                ), condition, "At the beginning of your upkeep, if you control the artifact " +
                "with the highest mana value or tied for the highest mana value, draw a card."
        ));
    }

    private PadeemConsulOfInnovation(final PadeemConsulOfInnovation card) {
        super(card);
    }

    @Override
    public PadeemConsulOfInnovation copy() {
        return new PadeemConsulOfInnovation(this);
    }
}
