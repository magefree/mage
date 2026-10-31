package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

/**
 * @author muz
 */
public final class FleetCaptainChristopherPike extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public FleetCaptainChristopherPike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(6);

        // Yes -- {1}, {T}: Untap two other target creatures.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(2, filter));
        this.addAbility(ability.withFlavorWord("Yes"));

        // No -- {2}, {T}: Tap two other target creatures.
        ability = new SimpleActivatedAbility(new TapTargetEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(2, filter));
        this.addAbility(ability.withFlavorWord("No"));
    }

    private FleetCaptainChristopherPike(final FleetCaptainChristopherPike card) {
        super(card);
    }

    @Override
    public FleetCaptainChristopherPike copy() {
        return new FleetCaptainChristopherPike(this);
    }
}
