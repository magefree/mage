
package mage.cards.c;

import java.util.UUID;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.KorSoldierToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ConquerorsPledge extends CardImpl {

    public ConquerorsPledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}{W}");

        this.addAbility(new KickerAbility("{6}"));

        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new CreateTokenEffect(new KorSoldierToken(), 12),
                new CreateTokenEffect(new KorSoldierToken(), 6), KickedCondition.ONCE,
                "Create six 1/1 white Kor Soldier creature tokens. If this spell was kicked, create twelve of those tokens instead"));
    }

    private ConquerorsPledge(final ConquerorsPledge card) {
        super(card);
    }

    @Override
    public ConquerorsPledge copy() {
        return new ConquerorsPledge(this);
    }

}
