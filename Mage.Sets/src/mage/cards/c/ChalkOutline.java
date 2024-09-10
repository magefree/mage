package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.DetectiveToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChalkOutline extends CardImpl {

    public ChalkOutline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Whenever one or more creature cards leave your graveyard, create a 2/2 white and blue Detective creature token, then investigate.
        Ability ability = new CardsLeaveGraveyardTriggeredAbility(
                new CreateTokenEffect(new DetectiveToken()), StaticFilters.FILTER_CARD_CREATURES
        );
        ability.addEffect(new InvestigateEffect().concatBy(", then"));
        this.addAbility(ability);
    }

    private ChalkOutline(final ChalkOutline card) {
        super(card);
    }

    @Override
    public ChalkOutline copy() {
        return new ChalkOutline(this);
    }
}
