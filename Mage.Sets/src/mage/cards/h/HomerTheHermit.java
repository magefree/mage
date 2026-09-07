package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPlayer;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HomerTheHermit extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("twice the number of Crabs, Lobsters, Nautiluses, Starfish, and/or Trilobites you control");

    static {
        filter.add(Predicates.or(
            SubType.CRAB.getPredicate(),
            SubType.LOBSTER.getPredicate(),
            SubType.NAUTILUS.getPredicate(),
            SubType.STARFISH.getPredicate(),
            SubType.TRILOBITE.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 2);
    private static final Hint hint = new ValueHint(
        "Crabs, Lobsters, Nautiluses, Starfish, and/or Trilobites you control", new PermanentsOnBattlefieldCount(filter)
    );

    public HomerTheHermit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CRAB);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(9);

        // Landfall -- Whenever a land you control enters, any number of target players each mill X cards, where X is twice the number of Crabs, Lobsters, Nautiluses, Starfish, and/or Trilobites you control.
        Ability ability = new LandfallAbility(
            new MillCardsTargetEffect(xValue)
                .setText("any number of target players each mill X cards, where X is twice the number of "
                    + "Crabs, Lobsters, Nautiluses, Starfish, and/or Trilobites you control")
        );
        ability.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(ability.addHint(hint));
    }

    private HomerTheHermit(final HomerTheHermit card) {
        super(card);
    }

    @Override
    public HomerTheHermit copy() {
        return new HomerTheHermit(this);
    }
}
