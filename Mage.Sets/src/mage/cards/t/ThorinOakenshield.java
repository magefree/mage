package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnduringStoryCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.EnduringStoryHint;
import mage.abilities.keyword.StoriedAbility;

/**
 *
 * @author muz
 */
public final class ThorinOakenshield extends CardImpl {

    public ThorinOakenshield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Storied
        this.addAbility(new StoriedAbility());

        // As long as you have an enduring story, artifacts and creatures you control have ward {1}.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(1)),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE
            ),
            EnduringStoryCondition.instance,
            "as long as you have an enduring story, artifacts and creatures you control have ward {1}"
        )).addHint(EnduringStoryHint.instance));
    }

    private ThorinOakenshield(final ThorinOakenshield card) {
        super(card);
    }

    @Override
    public ThorinOakenshield copy() {
        return new ThorinOakenshield(this);
    }
}
