package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SpiderToken;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class SpiderSpawning extends CardImpl {

    public SpiderSpawning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Create a 1/2 green Spider creature token with reach for each creature card in your graveyard.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiderToken(), new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE)));
        // Flashback {6}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{B}")));
    }

    private SpiderSpawning(final SpiderSpawning card) {
        super(card);
    }

    @Override
    public SpiderSpawning copy() {
        return new SpiderSpawning(this);
    }
}
