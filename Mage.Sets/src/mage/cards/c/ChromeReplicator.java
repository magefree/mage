package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.token.Construct4Token;
import mage.util.CardUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChromeReplicator extends CardImpl {

    public ChromeReplicator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Chrome Replicator enters the battlefield, if you control two or more nonland, nontoken permanents with the same name as one another, create a 4/4 colorless Construct artifact creature token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Construct4Token())),
                ChromeReplicatorCondition.instance, "When {this} enters the battlefield, " +
                "if you control two or more nonland, nontoken permanents with the same name as one another, " +
                "create a 4/4 colorless Construct artifact creature token."
        ));
    }

    private ChromeReplicator(final ChromeReplicator card) {
        super(card);
    }

    @Override
    public ChromeReplicator copy() {
        return new ChromeReplicator(this);
    }
}

enum ChromeReplicatorCondition implements Condition {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(TokenPredicate.FALSE);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<String, Integer> nameMap = new HashMap<>();
        return game
                .getBattlefield()
                .getActivePermanents(
                        filter, source.getControllerId(), source.getSourceId(), game
                ).stream()
                .filter(Objects::nonNull)
                .map(MageObject::getName)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .anyMatch(s -> nameMap.compute(s, CardUtil::setOrIncrementValue) >= 2);
    }
}
