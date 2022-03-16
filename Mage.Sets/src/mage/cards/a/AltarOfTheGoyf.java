package mage.cards.a;

import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author jmharmon
 */
public final class AltarOfTheGoyf extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent(SubType.LHURGOYF, "Lhurgoyf creatures");

    public AltarOfTheGoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ARTIFACT}, "{5}");

        this.subtype.add(SubType.LHURGOYF);

        // Whenever a creature you control attacks alone, it gets +X/+X until end of turn, where X is the number of card types among cards in all graveyard.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(new BoostTargetEffect(
                CardTypesInGraveyardCount.ALL,
                CardTypesInGraveyardCount.ALL,
                Duration.EndOfTurn, true
        ).setText("it gets +X/+X until end of turn, where X is " +
                "the number of card types among cards in all graveyards."), true, false).addHint(CardTypesInGraveyardHint.ALL));

        // Lhurgoyf creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private AltarOfTheGoyf(final AltarOfTheGoyf card) {
        super(card);
    }

    @Override
    public AltarOfTheGoyf copy() {
        return new AltarOfTheGoyf(this);
    }
}
