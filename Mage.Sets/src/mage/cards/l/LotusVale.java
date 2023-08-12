
package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.EnterBattlefieldPayCostOrPutGraveyardEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Luna Skyrise
 */
public final class LotusVale extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("untapped lands");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public LotusVale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // If Lotus Vale would enter the battlefield, sacrifice two untapped lands instead. If you do, put Lotus Vale onto the battlefield. If you don't, put it into its owner's graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new EnterBattlefieldPayCostOrPutGraveyardEffect(
                new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, false)))));

        // {tap}: Add three mana of any one color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(3), new TapSourceCost()));
    }

    private LotusVale(final LotusVale card) {
        super(card);
    }

    @Override
    public LotusVale copy() {
        return new LotusVale(this);
    }
}
