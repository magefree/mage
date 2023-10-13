
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class PurgeTheProfane extends CardImpl {

    public PurgeTheProfane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}{B}");


        // Target opponent discards two cards and you gain 2 life.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("and"));
    }

    private PurgeTheProfane(final PurgeTheProfane card) {
        super(card);
    }

    @Override
    public PurgeTheProfane copy() {
        return new PurgeTheProfane(this);
    }
}
