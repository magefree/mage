package mage.cards.t;

import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurnIntoAPumpkin extends CardImpl {

    public TurnIntoAPumpkin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Return target nonland permanent to its owner's hand. Draw a card.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).setText("Draw a card."));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // Adamant — If at least three blue mana was spent to cast this spell, create a Food token.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new FoodToken()), AdamantCondition.BLUE,
                "<br><i>Adamant</i> &mdash; If at least three blue mana " +
                        "was spent to cast this spell, create a Food token."
        ));
    }

    private TurnIntoAPumpkin(final TurnIntoAPumpkin card) {
        super(card);
    }

    @Override
    public TurnIntoAPumpkin copy() {
        return new TurnIntoAPumpkin(this);
    }
}
