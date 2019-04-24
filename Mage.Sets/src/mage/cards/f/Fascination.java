
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveEachPlayerEffect;
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
        this.getSpellAbility().addEffect(new DrawCardAllEffect(new ManacostVariableValue()));
        
        // * Each player puts the top X cards of their library into their graveyard.
        Mode mode = new Mode();
        mode.getEffects().add(new PutTopCardOfLibraryIntoGraveEachPlayerEffect(new ManacostVariableValue(), TargetController.ANY));
        this.getSpellAbility().addMode(mode);
    }

    public Fascination(final Fascination card) {
        super(card);
    }

    @Override
    public Fascination copy() {
        return new Fascination(this);
    }
}
