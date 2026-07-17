package mage.cards.t;

import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.Horror3Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheFinalDays extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
    private static final Hint hint = new ValueHint("Creature cards in your graveyard", xValue);

    public TheFinalDays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Create two tapped 2/2 black Horror creature tokens. If this spell was cast from a graveyard, instead create X of those tokens, where X is the number of creature cards in your graveyard.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new Horror3Token(), xValue, true, false), new CreateTokenEffect(new Horror3Token(), 2, true),
                CastFromGraveyardSourceCondition.instance, "create two tapped 2/2 black Horror creature tokens. " +
                "If this spell was cast from a graveyard, instead create X of those tokens, " +
                "where X is the number of creature cards in your graveyard"
        ));
        this.getSpellAbility().addHint(hint);

        // Flashback {4}{B}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{B}{B}")));
    }

    private TheFinalDays(final TheFinalDays card) {
        super(card);
    }

    @Override
    public TheFinalDays copy() {
        return new TheFinalDays(this);
    }
}
