
package mage.cards.u;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.game.permanent.token.KorAllyToken;

/**
 *
 * @author LevelX2
 */
public final class UnifiedFront extends CardImpl {

    public UnifiedFront(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}");

        // <i>Converge</i> &mdash; Create a 1/1 white Kor Ally creature token for each color of mana spent to cast Unified Front.
        getSpellAbility().setAbilityWord(AbilityWord.CONVERGE);
        Effect effect = new CreateTokenEffect(new KorAllyToken(), ColorsOfManaSpentToCastCount.getInstance());
        effect.setText("Create a 1/1 white Kor Ally creature token for each color of mana spent to cast {this}");
        getSpellAbility().addEffect(effect);
    }

    public UnifiedFront(final UnifiedFront card) {
        super(card);
    }

    @Override
    public UnifiedFront copy() {
        return new UnifiedFront(this);
    }
}
