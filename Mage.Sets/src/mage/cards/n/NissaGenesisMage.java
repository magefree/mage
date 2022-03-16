
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class NissaGenesisMage extends CardImpl {

    private static final FilterCard filter = new FilterCard("any number of creature and/or land cards");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public NissaGenesisMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{5}{G}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NISSA);

        this.setStartingLoyalty(5);

        //+2: Untap up to two target creatures and up to two target lands.
        Ability ability = new LoyaltyAbility(new UntapTargetEffect(false).setText("Untap up to two target creatures and up to two target lands"), +2);
        ability.addTarget(new TargetCreaturePermanent(0, 2, StaticFilters.FILTER_PERMANENT_CREATURES, false));
        ability.addTarget(new TargetLandPermanent(0, 2, StaticFilters.FILTER_LANDS, false));
        this.addAbility(ability);

        //-3: Target creature gets +5/+5 until end of turn.
        ability = new LoyaltyAbility(new BoostTargetEffect(5, 5, Duration.EndOfTurn), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        //-10: Look at the top ten cards of your library. You may put any number of creature and/or land cards from among them onto the battlefield. Put the rest on the bottom of your library in a random order.);
        this.addAbility(new LoyaltyAbility(
                new LookLibraryAndPickControllerEffect(StaticValue.get(10), false, StaticValue.get(10), filter,
                        Zone.LIBRARY, true, false, true, Zone.BATTLEFIELD, true, true, false).setBackInRandomOrder(true),
                -10));
    }

    private NissaGenesisMage(final NissaGenesisMage card) {
        super(card);
    }

    @Override
    public NissaGenesisMage copy() {
        return new NissaGenesisMage(this);
    }
}
