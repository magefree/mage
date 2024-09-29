package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ResilientRoadrunner extends CardImpl {

    private static final FilterCard filter = new FilterCard("Coyotes");
    private static final FilterCreaturePermanent filter2
            = new FilterCreaturePermanent("except by creatures with haste");

    static {
        filter.add(SubType.COYOTE.getPredicate());
        filter2.add(Predicates.not(new AbilityPredicate(HasteAbility.class)));
    }

    public ResilientRoadrunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Protection from Coyotes
        this.addAbility(new ProtectionAbility(filter));

        // {3}: Resilient Roadrunner can't be blocked this turn except by creatures with haste.
        this.addAbility(new SimpleActivatedAbility(
                new CantBeBlockedByCreaturesSourceEffect(filter2, Duration.EndOfTurn), new GenericManaCost(3)
        ));
    }

    private ResilientRoadrunner(final ResilientRoadrunner card) {
        super(card);
    }

    @Override
    public ResilientRoadrunner copy() {
        return new ResilientRoadrunner(this);
    }
}
// meep meep
