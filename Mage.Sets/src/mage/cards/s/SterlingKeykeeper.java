package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SterlingKeykeeper extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Mount creature");

    static {
        filter.add(Predicates.not(SubType.MOUNT.getPredicate()));
    }

    public SterlingKeykeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}, {T}: Tap target non-Mount creature.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SterlingKeykeeper(final SterlingKeykeeper card) {
        super(card);
    }

    @Override
    public SterlingKeykeeper copy() {
        return new SterlingKeykeeper(this);
    }
}
