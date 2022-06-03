package mage.cards.a;

import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArmsOfHadar extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures target player controls");

    static {
        filter.add(TargetController.SOURCE_TARGETS.getControllerPredicate());
    }

    public ArmsOfHadar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Creatures target player controls get -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                -2, -2, Duration.EndOfTurn, filter, false
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private ArmsOfHadar(final ArmsOfHadar card) {
        super(card);
    }

    @Override
    public ArmsOfHadar copy() {
        return new ArmsOfHadar(this);
    }
}
