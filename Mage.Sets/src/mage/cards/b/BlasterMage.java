
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LoneFox
 */
public final class BlasterMage extends CardImpl {

   private static final FilterPermanent filter = new FilterPermanent("Wall");

    static {
        filter.add(SubType.WALL.getPredicate());
    }

    public BlasterMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN, SubType.SPELLSHAPER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}, {tap}, Discard a card: Destroy target Wall.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BlasterMage(final BlasterMage card) {
        super(card);
    }

    @Override
    public BlasterMage copy() {
        return new BlasterMage(this);
    }
}
