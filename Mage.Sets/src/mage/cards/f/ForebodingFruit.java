package mage.cards.f;

import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FoodToken;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForebodingFruit extends CardImpl {

    public ForebodingFruit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player draws two cards and loses 2 life.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2).setText("and loses 2 life"));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Adamant — If at least three black mana was spent to cast this spell, create a Food token.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new FoodToken()), AdamantCondition.BLACK, "<br><i>Adamant</i> &mdash; " +
                "If at least three black mana was spent to cast this spell, create a Food token."
        ));
    }

    private ForebodingFruit(final ForebodingFruit card) {
        super(card);
    }

    @Override
    public ForebodingFruit copy() {
        return new ForebodingFruit(this);
    }
}
