
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.other.NumberOfTargetsPredicate;
import mage.target.common.TargetActivatedAbility;

/**
 *
 * @author LevelX2
 */
public final class Reroute extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("activated ability with a single target");

    static {
        filter.add(new NumberOfTargetsPredicate(1));
    }

    public Reroute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Change the target of target activated ability with a single target.
        this.getSpellAbility().addEffect(new ChooseNewTargetsTargetEffect(true, true));
        this.getSpellAbility().addTarget(new TargetActivatedAbility(filter));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Reroute(final Reroute card) {
        super(card);
    }

    @Override
    public Reroute copy() {
        return new Reroute(this);
    }
}
