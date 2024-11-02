package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RatToken;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author ciaccona007
 */
public final class RevengeOfTheRats extends CardImpl {

    private static final DynamicValue cardsCount = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);

    public RevengeOfTheRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        

        // Create a tapped 1/1 black Rat creature token for each creature card in your graveyard.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new RatToken(), cardsCount, true, false));
        this.getSpellAbility().addHint(new ValueHint("creature card card in your graveyard", cardsCount));

        // Flashback {2}{B}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{B}{B}")));

    }

    private RevengeOfTheRats(final RevengeOfTheRats card) {
        super(card);
    }

    @Override
    public RevengeOfTheRats copy() {
        return new RevengeOfTheRats(this);
    }
}
