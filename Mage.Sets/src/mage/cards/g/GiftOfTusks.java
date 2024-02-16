
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.ElephantToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class GiftOfTusks extends CardImpl {

    public GiftOfTusks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Until end of turn, target creature loses all abilities and becomes a green Elephant with base power and toughness 3/3.
        Effect effect = new BecomesCreatureTargetEffect(
                new ElephantToken(),
                true, false, Duration.EndOfTurn)
                .withDurationRuleAtStart(true);
        effect.setText("Until end of turn, target creature loses all abilities and becomes a green Elephant with base power and toughness 3/3");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GiftOfTusks(final GiftOfTusks card) {
        super(card);
    }

    @Override
    public GiftOfTusks copy() {
        return new GiftOfTusks(this);
    }
}
