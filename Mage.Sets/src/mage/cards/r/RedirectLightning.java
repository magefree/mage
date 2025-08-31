package mage.cards.r;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.other.NumberOfTargetsPredicate;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RedirectLightning extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("spell or ability with a single target");

    static {
        filter.add(new NumberOfTargetsPredicate(1));
    }

    public RedirectLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        this.subtype.add(SubType.LESSON);

        // As an additional cost to cast this spell, pay 5 life or pay {2}.
        this.getSpellAbility().addCost(new OrCost("pay 5 life or pay {2}", new PayLifeCost(5), new GenericManaCost(2)));

        // Change the target of target spell or ability with a single target.
        this.getSpellAbility().addEffect(new ChooseNewTargetsTargetEffect(true, true));
        this.getSpellAbility().addTarget(new TargetStackObject(filter));
    }

    private RedirectLightning(final RedirectLightning card) {
        super(card);
    }

    @Override
    public RedirectLightning copy() {
        return new RedirectLightning(this);
    }
}
