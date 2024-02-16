package mage.cards.f;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author openSrcCoder
 */
public final class FeedingFrenzy extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(
            new PermanentsOnBattlefieldCount(new FilterPermanent(SubType.ZOMBIE, "Zombies on the battlefield"), null)
    );

    public FeedingFrenzy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        // Target creature gets -X/-X until end of turn, where X is the number of Zombies on the battlefield.
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FeedingFrenzy(final FeedingFrenzy card) {
        super(card);
    }

    @Override
    public FeedingFrenzy copy() {
        return new FeedingFrenzy(this);
    }
}
