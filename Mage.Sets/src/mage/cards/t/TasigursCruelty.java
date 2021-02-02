
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class TasigursCruelty extends CardImpl {

    public TasigursCruelty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{B}");

        // Delve
        this.addAbility(new DelveAbility());

        // Each opponent discards two cards.
        this.getSpellAbility().addEffect(new DiscardEachPlayerEffect(StaticValue.get(2), false, TargetController.OPPONENT));
    }

    private TasigursCruelty(final TasigursCruelty card) {
        super(card);
    }

    @Override
    public TasigursCruelty copy() {
        return new TasigursCruelty(this);
    }
}
