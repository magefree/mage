package mage.cards.r;

import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.VarmintToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RiseOfTheVarmints extends CardImpl {

    public RiseOfTheVarmints(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");


        // Create X 2/1 green Varmint creature tokens, where X is the number of creature cards in your graveyard.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new VarmintToken(),
                new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE)
        ).setText("Create X 2/1 green Varmint creature tokens, where X is the number of creature cards in your graveyard."));

        // Plot {2}{G}
        this.addAbility(new PlotAbility("{2}{G}"));

    }

    private RiseOfTheVarmints(final RiseOfTheVarmints card) {
        super(card);
    }

    @Override
    public RiseOfTheVarmints copy() {
        return new RiseOfTheVarmints(this);
    }
}
