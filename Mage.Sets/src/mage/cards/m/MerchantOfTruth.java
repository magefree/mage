package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class MerchantOfTruth extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.CLUE, "Clues");

    public MerchantOfTruth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.ANGEL, SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a nontoken creature you control dies, investigate.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new InvestigateEffect(false), false, StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN
        ));

        // Clues you control have exalted.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new ExaltedAbility(), Duration.WhileOnBattlefield, filter
        ).setText("Clues you control have exalted. <i>(Whenever a creature you control attacks alone, that creature " +
                "gets +1/+1 until end of turn for each instance of exalted among permanents you control.)</i>"
        )));
    }

    private MerchantOfTruth(final MerchantOfTruth card) {
        super(card);
    }

    @Override
    public MerchantOfTruth copy() {
        return new MerchantOfTruth(this);
    }
}
