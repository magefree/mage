package mage.cards.y;

import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsNoSourcePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class Yare extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsNoSourcePredicate.instance);
    }

    public Yare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Target creature defending player controls gets +3/+0 until end of turn.
        // That creature can block up to two additional creatures this turn.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 0));
        this.getSpellAbility().addEffect(new CanBlockAdditionalCreatureTargetEffect(Duration.EndOfTurn, 2)
                .setText("That creature can block up to two additional creatures this turn"));

    }

    private Yare(final Yare card) {
        super(card);
    }

    @Override
    public Yare copy() {
        return new Yare(this);
    }
}
