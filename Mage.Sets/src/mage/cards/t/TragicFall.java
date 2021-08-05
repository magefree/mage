package mage.cards.t;

import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TragicFall extends CardImpl {

    public TragicFall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gets -3/-3 until end of turn.
        // Hellbent — That creature gets -13/-13 until end of turn instead if you have no cards in hand.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BoostTargetEffect(-13, -13), new BoostTargetEffect(-3, -3),
                new LockedInCondition(HellbentCondition.instance), "Target creature gets -3/-3 " +
                "until end of turn.<br><i>Hellbent</i> &mdash; That creature gets -13/-13 " +
                "until end of turn instead if you have no cards in hand"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TragicFall(final TragicFall card) {
        super(card);
    }

    @Override
    public TragicFall copy() {
        return new TragicFall(this);
    }
}
