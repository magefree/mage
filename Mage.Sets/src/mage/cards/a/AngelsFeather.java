
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AngelsFeather extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a white spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public AngelsFeather(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Whenever a player casts a white spell, you may gain 1 life.
        this.addAbility(new SpellCastAllTriggeredAbility(new GainLifeEffect(1), filter, true));
    }

    private AngelsFeather(final AngelsFeather card) {
        super(card);
    }

    @Override
    public AngelsFeather copy() {
        return new AngelsFeather(this);
    }

}
