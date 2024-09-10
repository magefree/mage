package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IorethOfTheHealingHouse extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("other target legendary creatures");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(AnotherPredicate.instance);
        filter2.add(SuperType.LEGENDARY.getPredicate());
    }

    public IorethOfTheHealingHouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {T}: Untap another target permanent.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {T}: Untap two other target legendary creatures.
        ability = new SimpleActivatedAbility(new UntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(2, filter2));
        this.addAbility(ability);
    }

    private IorethOfTheHealingHouse(final IorethOfTheHealingHouse card) {
        super(card);
    }

    @Override
    public IorethOfTheHealingHouse copy() {
        return new IorethOfTheHealingHouse(this);
    }
}
