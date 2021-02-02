
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class LostInALabyrinth extends CardImpl {

    public LostInALabyrinth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Target creature gets -3/-0 until end of turn. Scry 1.</i>
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3,-0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private LostInALabyrinth(final LostInALabyrinth card) {
        super(card);
    }

    @Override
    public LostInALabyrinth copy() {
        return new LostInALabyrinth(this);
    }
}
