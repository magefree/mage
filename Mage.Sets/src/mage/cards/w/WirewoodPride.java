
package mage.cards.w;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author djbrez
 */
public final class WirewoodPride extends CardImpl {

    private static final FilterCreaturePermanent elfCount = new FilterCreaturePermanent("Elves");
    static {
        elfCount.add(new SubtypePredicate(SubType.ELF));
    }
    
    public WirewoodPride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Target creature gets +X/+X until end of turn, where X is the number of Elves on the battlefield.
        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(elfCount);
        Effect effect = new BoostTargetEffect(amount, amount, Duration.EndOfTurn, true);
        effect.setText("Target creature gets +X/+X until end of turn, where X is the number of Elves on the battlefield");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public WirewoodPride(final WirewoodPride card) {
        super(card);
    }

    @Override
    public WirewoodPride copy() {
        return new WirewoodPride(this);
    }
}
