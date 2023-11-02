package mage.cards.c;

import mage.MageInt;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ComposerOfSpring extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_ENCHANTMENT, ComparisonType.MORE_THAN, 5
    );
    private static final Hint hint = new ValueHint(
            "Enchantments you control",
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_ENCHANTMENT)
    );

    public ComposerOfSpring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Constellation -- Whenever an enchantment enters the battlefield under your control, you may put a land card from your hand onto the battlefield tapped. If you control six or more enchantments, instead you may put a creature or land card from your hand onto the battlefield tapped.
        this.addAbility(new ConstellationAbility(new ConditionalOneShotEffect(
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_CREATURE_OR_LAND, false, true),
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A, false, true),
                condition, "you may put a land card from your hand onto the battlefield tapped. " +
                "If you control six or more enchantments, instead you may put a creature " +
                "or land card from your hand onto the battlefield tapped"
        ), false, false).addHint(hint));
    }

    private ComposerOfSpring(final ComposerOfSpring card) {
        super(card);
    }

    @Override
    public ComposerOfSpring copy() {
        return new ComposerOfSpring(this);
    }
}
