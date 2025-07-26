
package mage.cards.n;

import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NightDay extends SplitCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures target player controls");

    static {
        filter.add(TargetController.SOURCE_TARGETS.getControllerPredicate());
    }

    public NightDay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}", "{2}{W}", SpellAbilityType.SPLIT);

        // Night
        // Target creature gets -1/-1 until end of turn.
        getLeftHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(-1, -1, Duration.EndOfTurn));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Day
        // Creatures target player controls get +1/+1 until end of turn.
        getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer());
        getRightHalfCard().getSpellAbility().addEffect(new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, false));

    }

    private NightDay(final NightDay card) {
        super(card);
    }

    @Override
    public NightDay copy() {
        return new NightDay(this);
    }
}
