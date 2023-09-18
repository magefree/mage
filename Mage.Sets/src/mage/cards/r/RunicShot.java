package mage.cards.r;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RunicShot extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public RunicShot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");


        // Kicker {U}
        this.addAbility(new KickerAbility("{U}"));

        // Destroy target tapped creature. If this spell was kicked, scry 2.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ScryEffect(2), KickedCondition.ONCE,
                "if this spell was kicked, scry 2"
        ));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private RunicShot(final RunicShot card) {
        super(card);
    }

    @Override
    public RunicShot copy() {
        return new RunicShot(this);
    }
}
