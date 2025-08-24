
package mage.cards.c;

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
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrashLanding extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Forests you control");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public CrashLanding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Target creature with flying loses flying until end of turn. Crash Landing deals damage to that creature equal to the number of Forests you control.
        DynamicValue amount = new PermanentsOnBattlefieldCount(filter);
        this.getSpellAbility().addEffect(new LoseAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new DamageTargetEffect(amount).setText("{this} deals damage to that creature equal to the number of Forests you control"));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
    }

    private CrashLanding(final CrashLanding card) {
        super(card);
    }

    @Override
    public CrashLanding copy() {
        return new CrashLanding(this);
    }
}
