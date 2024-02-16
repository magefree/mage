
package mage.cards.c;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class CrashLanding extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Forests you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(SubType.FOREST.getPredicate());
        filter2.add(new AbilityPredicate(FlyingAbility.class));
    }

    public CrashLanding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Target creature with flying loses flying until end of turn. Crash Landing deals damage to that creature equal to the number of Forests you control.
        DynamicValue amount = new PermanentsOnBattlefieldCount(filter);
        this.getSpellAbility().addEffect(new LoseAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new DamageTargetEffect(amount).setText("{this} deals damage to that creature equal to the number of Forests you control"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter2));
    }

    private CrashLanding(final CrashLanding card) {
        super(card);
    }

    @Override
    public CrashLanding copy() {
        return new CrashLanding(this);
    }
}
