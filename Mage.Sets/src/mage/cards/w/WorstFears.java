
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.turn.ControlTargetPlayerNextTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class WorstFears extends CardImpl {

    public WorstFears(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{7}{B}");


        // You control target player during that player's next turn. Exile Worst Fears. (You see all cards that player could see and make all decisions for that player.)
        this.getSpellAbility().addEffect(new ControlTargetPlayerNextTurnEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private WorstFears(final WorstFears card) {
        super(card);
    }

    @Override
    public WorstFears copy() {
        return new WorstFears(this);
    }
}
