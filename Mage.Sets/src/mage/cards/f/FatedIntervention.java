
package mage.cards.f;

import java.util.UUID;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.CentaurEnchantmentCreatureToken;

/**
 *
 * @author LevelX2
 */
public final class FatedIntervention extends CardImpl {

    public FatedIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}{G}{G}");


        // Create two 3/3 green Centaur enchantment creature tokens. If it's your turn, scry 2.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new CentaurEnchantmentCreatureToken(), 2));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new ScryEffect(2), MyTurnCondition.instance, "If it's your turn, scry 2"));
    }

    public FatedIntervention(final FatedIntervention card) {
        super(card);
    }

    @Override
    public FatedIntervention copy() {
        return new FatedIntervention(this);
    }
}
