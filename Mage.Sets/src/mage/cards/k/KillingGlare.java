
package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KillingGlare extends CardImpl {

    public KillingGlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}");

        // Destroy target creature with power X or less.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("destroy target creature with power X or less"));
        this.getSpellAbility().setTargetAdjuster(KillingGlareAdjuster.instance);
    }

    private KillingGlare(final KillingGlare card) {
        super(card);
    }

    @Override
    public KillingGlare copy() {
        return new KillingGlare(this);
    }
}

enum KillingGlareAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        ability.getTargets().clear();
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power " + xValue + " or less");
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.addTarget(new TargetCreaturePermanent(filter));
    }
}