
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class IpnuRivulet extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Desert");

    static {
        filter.add(SubType.DESERT.getPredicate());
    }

    public IpnuRivulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // {t}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {t}, Pay 1 life: Add {U}.
        Ability manaAbility = new BlueManaAbility();
        manaAbility.addCost(new PayLifeCost(1));
        this.addAbility(manaAbility);

        // {1}{U}, {t}, Sacrifice a Desert: Target player puts the top four cards of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutLibraryIntoGraveTargetEffect(4), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, true)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private IpnuRivulet(final IpnuRivulet card) {
        super(card);
    }

    @Override
    public IpnuRivulet copy() {
        return new IpnuRivulet(this);
    }
}
