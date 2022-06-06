
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.EnterBattlefieldPayCostOrPutGraveyardEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.SoldierToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class KjeldoranOutpost extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Plains");

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    public KjeldoranOutpost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // If Kjeldoran Outpost would enter the battlefield, sacrifice a Plains instead. If you do, put Kjeldoran Outpost onto the battlefield. If you don't, put it into its owner's graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new EnterBattlefieldPayCostOrPutGraveyardEffect(new SacrificeTargetCost(new TargetControlledPermanent(filter)))));
        // {tap}: Add {W}.
        this.addAbility(new WhiteManaAbility());
        // {1}{W}, {tap}: Create a 1/1 white Soldier creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SoldierToken()), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private KjeldoranOutpost(final KjeldoranOutpost card) {
        super(card);
    }

    @Override
    public KjeldoranOutpost copy() {
        return new KjeldoranOutpost(this);
    }
}
