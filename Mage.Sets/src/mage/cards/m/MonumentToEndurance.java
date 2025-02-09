package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonumentToEndurance extends CardImpl {

    public MonumentToEndurance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever you discard a card, choose one that hasn't been chosen this turn--
        // * Draw a card.
        Ability ability = new DiscardCardControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        );
        ability.getModes().setLimitUsageByOnce(true);

        // * Create a Treasure token.
        ability.addMode(new Mode(new CreateTokenEffect(new TreasureToken())));

        // * Each opponent loses 3 life.
        ability.addMode(new Mode(new LoseLifeOpponentsEffect(3)));
        this.addAbility(ability);
    }

    private MonumentToEndurance(final MonumentToEndurance card) {
        super(card);
    }

    @Override
    public MonumentToEndurance copy() {
        return new MonumentToEndurance(this);
    }
}
