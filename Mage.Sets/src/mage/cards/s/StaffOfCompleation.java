package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public final class StaffOfCompleation extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public StaffOfCompleation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}, Pay 1 life: Destroy target permanent you own.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {T}, Pay 2 life: Add one mana of any color.
        ability = new AnyColorManaAbility();
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability);

        // {T}, Pay 3 life: Proliferate.
        ability = new SimpleActivatedAbility(new ProliferateEffect(), new TapSourceCost());
        ability.addCost(new PayLifeCost(3));
        this.addAbility(ability);

        // {T}, Pay 4 life: Draw a card.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new PayLifeCost(4));
        this.addAbility(ability);

        // {5}: Untap Staff of Compleation.
        this.addAbility(new SimpleActivatedAbility(new UntapSourceEffect(), new GenericManaCost(5)));
    }

    private StaffOfCompleation(final StaffOfCompleation card) {
        super(card);
    }

    @Override
    public StaffOfCompleation copy() {
        return new StaffOfCompleation(this);
    }
}
