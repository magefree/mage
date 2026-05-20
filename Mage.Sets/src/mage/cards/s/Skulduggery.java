
package mage.cards.s;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import static mage.filter.StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801, Susucr
 */
public final class Skulduggery extends CardImpl {

    public Skulduggery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Until end of turn, target creature you control gets +1/+1 and target creature an opponent controls gets -1/-1.
        this.getSpellAbility().addEffect(
                new BoostTargetEffect(1, 1)
                        .setText("Until end of turn, target creature you control gets +1/+1"));
        this.getSpellAbility().addEffect(
                new BoostTargetEffect(-1, -1)
                        .setTargetPointer(new SecondTargetPointer())
                        .setText("and target creature an opponent controls gets -1/-1."));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(FILTER_OPPONENTS_PERMANENT_CREATURE));
    }

    private Skulduggery(final Skulduggery card) {
        super(card);
    }

    @Override
    public Skulduggery copy() {
        return new Skulduggery(this);
    }
}