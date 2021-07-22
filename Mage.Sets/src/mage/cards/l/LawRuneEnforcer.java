package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LawRuneEnforcer extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature with mana value 2 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 1));
    }

    public LawRuneEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {1}, {T}: Tap target creature with converted mana cost 2 or greater.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private LawRuneEnforcer(final LawRuneEnforcer card) {
        super(card);
    }

    @Override
    public LawRuneEnforcer copy() {
        return new LawRuneEnforcer(this);
    }
}
