package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.XiraBlackInsectToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScouringSwarm extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(7, StaticFilters.FILTER_CARD_LAND);
    private static final Hint hint = new ValueHint(
            "Lands in your graveyard", new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_LAND)
    );

    public ScouringSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you sacrifice a land, create a tapped token that's a copy of this creature if seven or more land cards are in your graveyard. Otherwise, create a tapped 1/1 black Insect creature token with flying.
        this.addAbility(new SacrificePermanentTriggeredAbility(new ConditionalOneShotEffect(
                new CreateTokenCopySourceEffect(1, true),
                new CreateTokenEffect(new XiraBlackInsectToken(), 1, true),
                condition, "create a tapped token that's a copy of this creature " +
                "if seven or more land cards are in your graveyard. Otherwise, create a " +
                "tapped 1/1 black Insect creature token with flying"
        ), StaticFilters.FILTER_LAND).addHint(hint));
    }

    private ScouringSwarm(final ScouringSwarm card) {
        super(card);
    }

    @Override
    public ScouringSwarm copy() {
        return new ScouringSwarm(this);
    }
}
