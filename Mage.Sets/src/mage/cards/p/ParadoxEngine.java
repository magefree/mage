
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterNonlandPermanent;

/**
 *
 * @author fireshoes
 */
public final class ParadoxEngine extends CardImpl {

    public ParadoxEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.addSuperType(SuperType.LEGENDARY);

        // Whenever you cast a spell, untap all nonland permanents you control.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapAllControllerEffect(new FilterNonlandPermanent()), false));
    }

    private ParadoxEngine(final ParadoxEngine card) {
        super(card);
    }

    @Override
    public ParadoxEngine copy() {
        return new ParadoxEngine(this);
    }
}
