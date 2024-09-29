package mage.cards.t;

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
 * @author xenohedron
 */
public final class TeamSpirit extends CardImpl {

    // "team" not yet supported, so implementing as "creatures target player controls"
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures target player's team controls");

    static {
        filter.add(TargetController.SOURCE_TARGETS.getControllerPredicate());
    }

    public TeamSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Creatures target player's team controls get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, false));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private TeamSpirit(final TeamSpirit card) {
        super(card);
    }

    @Override
    public TeamSpirit copy() {
        return new TeamSpirit(this);
    }
}
