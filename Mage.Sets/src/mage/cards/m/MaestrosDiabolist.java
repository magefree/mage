package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.DevilToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaestrosDiabolist extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DEVIL);

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public MaestrosDiabolist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Maestros Diabolist attacks, if you don't control a Devil token, create a tapped and attacking 1/1 red Devil creature token with "When this creature dies, it deals 1 damage to any target."
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(
                        new CreateTokenEffect(new DevilToken(), 1, true, true)
                ), condition, "Whenever {this} attacks, if you don't control a Devil token, " +
                "create a tapped and attacking 1/1 red Devil creature token with " +
                "\"When this creature dies, it deals 1 damage to any target.\""
        ));
    }

    private MaestrosDiabolist(final MaestrosDiabolist card) {
        super(card);
    }

    @Override
    public MaestrosDiabolist copy() {
        return new MaestrosDiabolist(this);
    }
}
