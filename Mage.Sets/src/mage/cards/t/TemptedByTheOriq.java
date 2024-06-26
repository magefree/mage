package mage.cards.t;

import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.EachOpponentPermanentTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemptedByTheOriq extends CardImpl {
    private static final FilterCreatureOrPlaneswalkerPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }
    public TemptedByTheOriq(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{U}{U}");

        // For each opponent, gain control of up to one target creature or planeswalker that player controls with mana value 3 or less.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom, true)
                .setTargetPointer(new EachTargetPointer())
                .setText("for each opponent, gain control of up to one target creature " +
                        "or planeswalker that player controls with mana value 3 or less"));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter));
        this.getSpellAbility().setTargetAdjuster(new EachOpponentPermanentTargetsAdjuster());
    }

    private TemptedByTheOriq(final TemptedByTheOriq card) {
        super(card);
    }

    @Override
    public TemptedByTheOriq copy() {
        return new TemptedByTheOriq(this);
    }
}
