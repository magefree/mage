package mage.cards.g;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author muz
 */
public final class GnashingOfTeeth extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures target opponent controls");

    static {
        filter.add(TargetController.SOURCE_TARGETS.getControllerPredicate());
    }

    public GnashingOfTeeth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Choose one --
        // * Target creature gets -5/-5 until end of turn. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-5, -5, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // * Creatures target player controls get -1/-1 until end of turn.
        this.getSpellAbility().addMode(
            new Mode(
                new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter, false)
            ).addTarget(new TargetOpponent())
        );
    }

    private GnashingOfTeeth(final GnashingOfTeeth card) {
        super(card);
    }

    @Override
    public GnashingOfTeeth copy() {
        return new GnashingOfTeeth(this);
    }
}
