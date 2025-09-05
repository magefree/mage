package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.MayhemAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class SandmansQuicksand extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SandmansQuicksand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");
        

        // Mayhem {3}{B}
        Ability mayhem = new MayhemAbility(this, "{3}{B}");
        mayhem.addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn, filter, false));
        this.addAbility(mayhem);

        // All creatures get -2/-2 until end of turn. If this spell's mayhem cost was paid, creatures your opponents control get -2/-2 until end of turn instead.
        ContinuousEffect effect = new BoostAllEffect(-2, -2, Duration.EndOfTurn);
        effect.setText("All creatures get -2/-2 until end of turn. If this spell's mayhem cost was paid, " +
                        "creatures your opponents control get -2/-2 until end of turn instead"
        );
        this.getSpellAbility().addEffect(effect);

    }

    private SandmansQuicksand(final SandmansQuicksand card) {
        super(card);
    }

    @Override
    public SandmansQuicksand copy() {
        return new SandmansQuicksand(this);
    }
}
