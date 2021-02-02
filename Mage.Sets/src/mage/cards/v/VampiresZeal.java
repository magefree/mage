
package mage.cards.v;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.TargetHasSubtypeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class VampiresZeal extends CardImpl {

    public VampiresZeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target creature gets +2/+2 until end of turn.  If it's a Vampire, it gains first strike until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), new LockedInCondition(new TargetHasSubtypeCondition(SubType.VAMPIRE)),
                "If it's a Vampire, it gains first strike until end of turn"));
    }

    private VampiresZeal(final VampiresZeal card) {
        super(card);
    }

    @Override
    public VampiresZeal copy() {
        return new VampiresZeal(this);
    }
}
