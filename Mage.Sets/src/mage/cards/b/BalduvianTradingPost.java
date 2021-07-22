
package mage.cards.b;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.EnterBattlefieldPayCostOrPutGraveyardEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class BalduvianTradingPost extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an untapped Mountain");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public BalduvianTradingPost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // If Balduvian Trading Post would enter the battlefield, sacrifice an untapped Mountain instead. If you do, put Balduvian Trading Post onto the battlefield. If you don't, put it into its owner's graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new EnterBattlefieldPayCostOrPutGraveyardEffect(new SacrificeTargetCost(new TargetControlledPermanent(filter)))));

        // {tap}: Add {C}{R}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 0, 0, 1, 0, 0, 0, 1), new TapSourceCost()));

        // {1}, {tap}: Balduvian Trading Post deals 1 damage to target attacking creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private BalduvianTradingPost(final BalduvianTradingPost card) {
        super(card);
    }

    @Override
    public BalduvianTradingPost copy() {
        return new BalduvianTradingPost(this);
    }
}
