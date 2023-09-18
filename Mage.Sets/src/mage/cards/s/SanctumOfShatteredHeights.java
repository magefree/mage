package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class SanctumOfShatteredHeights extends CardImpl {

    private static final FilterCard filter = new FilterCard("a land card or Shrine card");
    private static final FilterPermanent filterShrinesOnly = new FilterControlledPermanent("Shrine you control");
    private static final PermanentsOnBattlefieldCount xValue = new PermanentsOnBattlefieldCount(filterShrinesOnly);

    static {
        filter.add(Predicates.or(CardType.LAND.getPredicate(), SubType.SHRINE.getPredicate()));
        filterShrinesOnly.add(SubType.SHRINE.getPredicate());
    }

    public SanctumOfShatteredHeights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // {1}, Discard a land card or Shrine card: Sanctum of Shattered Heights deals X damage to target creature or planeswalker, where X is the number of Shrines you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(xValue)
                .setText("Sanctum of Shattered Heights deals X damage to target creature or planeswalker, where X is the number of Shrines you control"),
                new ManaCostsImpl<>("{1}"))
                .addHint(new ValueHint("Shrines you control", xValue));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(filter)));
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);
    }

    private SanctumOfShatteredHeights(final SanctumOfShatteredHeights card) {
        super(card);
    }

    @Override
    public SanctumOfShatteredHeights copy() {
        return new SanctumOfShatteredHeights(this);
    }
}
