
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.SnakeToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth

 */
public final class Snakeform extends CardImpl {

    public Snakeform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G/U}");


        // Until end of turn, target creature loses all abilities and becomes a green Snake with base power and toughness 1/1.
        Effect effect = new BecomesCreatureTargetEffect(new SnakeToken(), true, false, Duration.EndOfTurn)
                .withDurationRuleAtStart(true);
        effect.setText("Until end of turn, target creature loses all abilities and becomes a green Snake with base power and toughness 1/1");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
        
    }

    private Snakeform(final Snakeform card) {
        super(card);
    }

    @Override
    public Snakeform copy() {
        return new Snakeform(this);
    }
}
