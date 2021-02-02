package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class RampagingBrontodon extends CardImpl {

    static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public RampagingBrontodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Rampaging Brontodon attacks, it gets +1/+1 until end of turn for each land you control.
        DynamicValue landsCount = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND);
        this.addAbility(new AttacksTriggeredAbility(
                new BoostSourceEffect(landsCount, landsCount, Duration.EndOfTurn).setText("it gets +1/+1 until end of turn for each land you control"),
                false)
                .addHint(new ValueHint("Lands you control", landsCount)));
    }

    private RampagingBrontodon(final RampagingBrontodon card) {
        super(card);
    }

    @Override
    public RampagingBrontodon copy() {
        return new RampagingBrontodon(this);
    }
}
