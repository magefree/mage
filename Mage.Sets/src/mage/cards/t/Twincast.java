
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author Loki
 */
public final class Twincast extends CardImpl {

    public Twincast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{U}");

        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CopyTargetSpellEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public Twincast(final Twincast card) {
        super(card);
    }

    @Override
    public Twincast copy() {
        return new Twincast(this);
    }
}
