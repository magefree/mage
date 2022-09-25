
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author TheElk801
 */
public final class SaprolingMigration extends CardImpl {

    public SaprolingMigration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Kicker {4}
        this.addAbility(new KickerAbility("{4}"));

        // Create two 1/1 green saproling creature tokens. If this spell was kicked, create four of those tokens instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new CreateTokenEffect(new SaprolingToken(), 4),
                new CreateTokenEffect(new SaprolingToken(), 2), KickedCondition.ONCE,
                "Create two 1/1 green Saproling creature tokens. If this spell was kicked, create four of those tokens instead"));
    }

    private SaprolingMigration(final SaprolingMigration card) {
        super(card);
    }

    @Override
    public SaprolingMigration copy() {
        return new SaprolingMigration(this);
    }
}
