
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Backfir3
 */
public final class Wildfire extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("lands");

    public Wildfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}{R}");


        //Each player sacrifices four lands.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(4, filter));
        //Wildfire deals 4 damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(4, StaticFilters.FILTER_PERMANENT_CREATURE));
    }

    private Wildfire(final Wildfire card) {
        super(card);
    }

    @Override
    public Wildfire copy() {
        return new Wildfire(this);
    }
}
