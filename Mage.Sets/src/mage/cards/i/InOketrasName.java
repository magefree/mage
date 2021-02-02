
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author fireshoes
 */
public final class InOketrasName extends CardImpl {

    private static final FilterCreaturePermanent filterNotZombies = new FilterCreaturePermanent("Other creatures");

    static {
        filterNotZombies.add(Predicates.not(SubType.ZOMBIE.getPredicate()));
    }

    public InOketrasName(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Zombies you control get +2/+1 until end of turn. Other creatures you control get +1/+1 until end of turn.
        getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn, new FilterCreaturePermanent(SubType.ZOMBIE, "Zombies")));
        getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn, filterNotZombies));
    }

    private InOketrasName(final InOketrasName card) {
        super(card);
    }

    @Override
    public InOketrasName copy() {
        return new InOketrasName(this);
    }
}
