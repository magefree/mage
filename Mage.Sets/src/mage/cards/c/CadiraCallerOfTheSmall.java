package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.RabbitToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CadiraCallerOfTheSmall extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Tokens you control", xValue);

    public CadiraCallerOfTheSmall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Cadira, Caller of the Small deals combat damage to a player, for each token you control, create a 1/1 white Rabbit creature token.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(new RabbitToken(), xValue)
                        .setText("for each token you control, create a 1/1 white Rabbit creature token"),
                false
        ).addHint(hint));
    }

    private CadiraCallerOfTheSmall(final CadiraCallerOfTheSmall card) {
        super(card);
    }

    @Override
    public CadiraCallerOfTheSmall copy() {
        return new CadiraCallerOfTheSmall(this);
    }
}
