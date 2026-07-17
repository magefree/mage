package mage.cards.c;

import java.util.UUID;

import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.TeamworkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author muz
 */
public final class CrossoverCollaboration extends CardImpl {

    public CrossoverCollaboration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Teamwork 2
        this.addAbility(new TeamworkAbility(2));

        // Exile the top two cards of your library. Until the end of your next turn, you may play those cards. If this spell was cast using teamwork, create a Treasure token.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEffect(
            2, Duration.UntilEndOfYourNextTurn
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new CreateTokenEffect(new TreasureToken()),
            TeamworkCondition.instance,
            "If this spell was cast using teamwork, create a Treasure token"
        ));
    }

    private CrossoverCollaboration(final CrossoverCollaboration card) {
        super(card);
    }

    @Override
    public CrossoverCollaboration copy() {
        return new CrossoverCollaboration(this);
    }
}
