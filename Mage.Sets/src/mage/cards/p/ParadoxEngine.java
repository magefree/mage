
package mage.cards.p;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ParadoxEngine extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanents");

    public ParadoxEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever you cast a spell, untap all nonland permanents you control.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapAllControllerEffect(filter), false));
    }

    private ParadoxEngine(final ParadoxEngine card) {
        super(card);
    }

    @Override
    public ParadoxEngine copy() {
        return new ParadoxEngine(this);
    }
}
