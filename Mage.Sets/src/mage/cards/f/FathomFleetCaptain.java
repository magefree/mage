
package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.PirateToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FathomFleetCaptain extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.PIRATE, "you control another nontoken Pirate");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public FathomFleetCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Fathom Fleet Captain attacks, if you control another nontoken Pirate, you may pay {2}. If you do, creature a 2/2 black Pirate creature token with menace.
        this.addAbility(new AttacksTriggeredAbility(
                new DoIfCostPaid(new CreateTokenEffect(new PirateToken()), new GenericManaCost(2))
        ).withInterveningIf(condition));
    }

    private FathomFleetCaptain(final FathomFleetCaptain card) {
        super(card);
    }

    @Override
    public FathomFleetCaptain copy() {
        return new FathomFleetCaptain(this);
    }
}
