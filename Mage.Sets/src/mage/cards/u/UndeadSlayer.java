
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class UndeadSlayer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Skeleton, Vampire, or Zombie");

    static {
        filter.add(Predicates.or(
                new SubtypePredicate(SubType.SKELETON),
                new SubtypePredicate(SubType.VAMPIRE),
                new SubtypePredicate(SubType.ZOMBIE)));
    }

    public UndeadSlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ColoredManaCost(ColoredManaSymbol.W));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public UndeadSlayer(final UndeadSlayer card) {
        super(card);
    }

    @Override
    public UndeadSlayer copy() {
        return new UndeadSlayer(this);
    }
}
