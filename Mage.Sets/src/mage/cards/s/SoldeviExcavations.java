
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.EnterBattlefieldPayCostOrPutGraveyardEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class SoldeviExcavations extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an untapped Island");

    static {
        filter.add(SubType.ISLAND.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SoldeviExcavations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // If Soldevi Excavations would enter the battlefield, sacrifice an untapped Island instead. If you do, put Soldevi Excavations onto the battlefield. If you don't, put it into its owner's graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new EnterBattlefieldPayCostOrPutGraveyardEffect(new SacrificeTargetCost(new TargetControlledPermanent(filter)))));

        // {tap}: Add {C}{U}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 1, 0, 0, 0, 0, 0, 1), new TapSourceCost()));

        // {1}, {tap}: Scry 1.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(1, false), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SoldeviExcavations(final SoldeviExcavations card) {
        super(card);
    }

    @Override
    public SoldeviExcavations copy() {
        return new SoldeviExcavations(this);
    }
}
