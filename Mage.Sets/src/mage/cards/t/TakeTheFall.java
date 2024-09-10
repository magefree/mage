package mage.cards.t;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TakeTheFall extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("an outlaw");

    static {
        filter.add(OutlawPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, true);
    private static final Hint hint = new ConditionHint(condition, "you control an outlaw");

    public TakeTheFall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Target creature gets -1/-0 until end of turn. It gets -4/-0 until end of turn instead if you control an outlaw.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BoostTargetEffect(-4, 0, Duration.EndOfTurn),
                new BoostTargetEffect(-1, 0, Duration.EndOfTurn),
                condition,
                "Target creature gets -1/-0 until end of turn. It gets -4/-0 until end of turn instead if you control an outlaw."));
        this.getSpellAbility().addHint(hint);

        // Draw a card.
        this.getSpellAbility().addEffect(
                new DrawCardSourceControllerEffect(1)
                        .concatBy("<br>")
        );
    }

    private TakeTheFall(final TakeTheFall card) {
        super(card);
    }

    @Override
    public TakeTheFall copy() {
        return new TakeTheFall(this);
    }
}
