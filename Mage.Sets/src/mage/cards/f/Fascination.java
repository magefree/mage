
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author emerald000
 */
public final class Fascination extends CardImpl {

    public Fascination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{U}{U}");

        // Choose one -
        // * Each player draws X cards.
        this.getSpellAbility().addEffect(new DrawCardAllEffect(ManacostVariableValue.REGULAR));
        
        // * Each player puts the top X cards of their library into their graveyard.
        Mode mode = new Mode(new MillCardsEachPlayerEffect(ManacostVariableValue.REGULAR, TargetController.ANY));
        this.getSpellAbility().addMode(mode);
    }

    private Fascination(final Fascination card) {
        super(card);
    }

    @Override
    public Fascination copy() {
        return new Fascination(this);
    }
}
