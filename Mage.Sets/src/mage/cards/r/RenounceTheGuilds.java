

package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

/**
 *
 * @author LevelX2
 */


public final class RenounceTheGuilds extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("multicolored permanent");
    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public RenounceTheGuilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Each player sacrifices a multicolored permanent.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(filter));
    }

    private RenounceTheGuilds(final RenounceTheGuilds card) {
        super(card);
    }

    @Override
    public RenounceTheGuilds copy() {
        return new RenounceTheGuilds(this);
    }
}
