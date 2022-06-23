package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeleportationCircle extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("artifact or creature you control");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public TeleportationCircle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // At the beginning of your end step, exile up to one target artifact or creature you control, then return that card to the battlefield under its owner's control.
        Ability ability = new BeginningOfYourEndStepTriggeredAbility(new ExileTargetForSourceEffect(), false);
        ability.addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private TeleportationCircle(final TeleportationCircle card) {
        super(card);
    }

    @Override
    public TeleportationCircle copy() {
        return new TeleportationCircle(this);
    }
}
