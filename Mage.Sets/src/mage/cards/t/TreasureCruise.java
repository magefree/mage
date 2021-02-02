
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class TreasureCruise extends CardImpl {

    public TreasureCruise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{7}{U}");


        // Delve
        this.addAbility(new DelveAbility());
        // Draw 3 Cards
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
    }

    private TreasureCruise(final TreasureCruise card) {
        super(card);
    }

    @Override
    public TreasureCruise copy() {
        return new TreasureCruise(this);
    }
}
