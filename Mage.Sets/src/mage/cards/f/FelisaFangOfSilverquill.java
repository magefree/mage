package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.InklingToken;

import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FelisaFangOfSilverquill extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a nontoken creature you control");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(CounterAnyPredicate.instance);
    }

    private static final String rule = "if it had counters on it, " +
            "create X tapped 2/1 white and black Inkling creature tokens with flying, " +
            "where X is the number of counters it had on it";

    public FelisaFangOfSilverquill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Mentor
        this.addAbility(new MentorAbility());

        // Whenever a nontoken creature you control dies, if it had counters on it, create X tapped 2/1 white and black Inkling creature tokens with flying, where X is the number of counters it had on it.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(
                new InklingToken(), FelisaFangOfSilverquillValue.instance, true, false
        ).setText(rule), false, filter));
    }

    private FelisaFangOfSilverquill(final FelisaFangOfSilverquill card) {
        super(card);
    }

    @Override
    public FelisaFangOfSilverquill copy() {
        return new FelisaFangOfSilverquill(this);
    }
}

enum FelisaFangOfSilverquillValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = (Permanent) effect.getValue("creatureDied");
        if (permanent == null) {
            return 0;
        }
        return permanent
                .getCounters(game)
                .entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .mapToInt(Counter::getCount)
                .sum();
    }

    @Override
    public FelisaFangOfSilverquillValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
