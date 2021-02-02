
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class TelJiladStylus extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();
    
    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }
    
    public TelJiladStylus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {T}: Put target permanent you own on the bottom of your library.
        Effect effect = new PutOnLibraryTargetEffect(false,"put target permanent you own on the bottom of your library");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TelJiladStylus(final TelJiladStylus card) {
        super(card);
    }

    @Override
    public TelJiladStylus copy() {
        return new TelJiladStylus(this);
    }
}
