
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
*
* @author LevelX2
*/
public final class Overwhelm extends CardImpl {

    public Overwhelm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{G}{G}");      
      
        // Convoke (Each creature you tap while casting this spell reduces its cost by {1} or by one mana of that creature's color.)
        this.addAbility(new ConvokeAbility());

        // Creatures you control get +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(3, 3, Duration.EndOfTurn));
    }

    private Overwhelm(final Overwhelm card) {
        super(card);
    }

    @Override
    public Overwhelm copy() {
        return new Overwhelm(this);
    }
}
