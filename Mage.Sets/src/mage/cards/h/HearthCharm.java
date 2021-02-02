
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class HearthCharm extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("artifact creature");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("attacking creatures");
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent("creature with power 2 or less");
    static {
        filter1.add(CardType.ARTIFACT.getPredicate());
        filter2.add(AttackingPredicate.instance);
        filter3.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public HearthCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Choose one - Destroy target artifact creature
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter1));
        // or attacking creatures get +1/+0 until end of turn
        Mode mode = new Mode();
        mode.addEffect(new BoostAllEffect(1, 0, Duration.EndOfTurn, filter2, false));
        this.getSpellAbility().addMode(mode);
        // or target creature with power 2 or less is unblockable this turn.
        mode = new Mode();
        mode.addEffect(new CantBeBlockedTargetEffect());
        mode.addTarget(new TargetCreaturePermanent(filter3));
        this.getSpellAbility().addMode(mode);
    }

    private HearthCharm(final HearthCharm card) {
        super(card);
    }

    @Override
    public HearthCharm copy() {
        return new HearthCharm(this);
    }
}
