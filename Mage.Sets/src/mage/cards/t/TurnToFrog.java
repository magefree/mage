
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.FrogToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nantuko
 */
public final class TurnToFrog extends CardImpl {

    public TurnToFrog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Until end of turn, target creature loses all abilities and becomes a blue Frog with base power and toughness 1/1.
        Effect effect = new BecomesCreatureTargetEffect(new FrogToken(), true, false, Duration.EndOfTurn);
        effect.setText("Until end of turn, target creature loses all abilities and becomes a blue Frog with base power and toughness 1/1");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TurnToFrog(final TurnToFrog card) {
        super(card);
    }

    @Override
    public TurnToFrog copy() {
        return new TurnToFrog(this);
    }
}
