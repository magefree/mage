
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class MeditationPuzzle extends CardImpl {

    public MeditationPuzzle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}{W}");


        // Convoke
        this.addAbility(new ConvokeAbility());
        // You gain 8 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(8));
    }

    private MeditationPuzzle(final MeditationPuzzle card) {
        super(card);
    }

    @Override
    public MeditationPuzzle copy() {
        return new MeditationPuzzle(this);
    }
}
