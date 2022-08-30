package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.BlockingCreatureCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class BerserkMurlodont extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.BEAST, "a Beast");

    public BerserkMurlodont(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Beast becomes blocked, it gets +1/+1 until end of turn for each creature blocking it.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(
                new BoostTargetEffect(BlockingCreatureCount.TARGET, BlockingCreatureCount.TARGET, Duration.EndOfTurn),
                false, filter, true
        ));
    }

    private BerserkMurlodont(final BerserkMurlodont card) {
        super(card);
    }

    @Override
    public BerserkMurlodont copy() {
        return new BerserkMurlodont(this);
    }
}
