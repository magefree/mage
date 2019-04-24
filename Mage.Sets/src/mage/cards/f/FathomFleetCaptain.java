
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.PirateToken;

/**
 *
 * @author TheElk801
 */
public final class FathomFleetCaptain extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another nontoken Pirate");

    static {
        filter.add(new SubtypePredicate(SubType.PIRATE));
        filter.add(new AnotherPredicate());
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public FathomFleetCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Fathom Fleet Captain attacks, if you control another nontoken Pirate, you may pay {2}. If you do, creature a 2/2 black Pirate creature token with menace.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new DoIfCostPaid(new CreateTokenEffect(new PirateToken()), new GenericManaCost(2)), false),
                new PermanentsOnTheBattlefieldCondition(filter),
                "Whenever {this} attacks, if you control another nontoken Pirate, you may pay {2}. If you do, create a 2/2 black Pirate creature token with menace"));
    }

    public FathomFleetCaptain(final FathomFleetCaptain card) {
        super(card);
    }

    @Override
    public FathomFleetCaptain copy() {
        return new FathomFleetCaptain(this);
    }
}
