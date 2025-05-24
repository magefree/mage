package mage.cards.f;

import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.CentaurEnchantmentCreatureToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FatedIntervention extends CardImpl {

    public FatedIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}{G}{G}");

        // Create two 3/3 green Centaur enchantment creature tokens. If it's your turn, scry 2.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new CentaurEnchantmentCreatureToken(), 2));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new ScryEffect(2, false), MyTurnCondition.instance, "If it's your turn, scry 2"));
        this.getSpellAbility().addHint(MyTurnHint.instance);
    }

    private FatedIntervention(final FatedIntervention card) {
        super(card);
    }

    @Override
    public FatedIntervention copy() {
        return new FatedIntervention(this);
    }
}
