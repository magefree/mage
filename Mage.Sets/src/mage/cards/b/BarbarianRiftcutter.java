
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Loki
 */
public final class BarbarianRiftcutter extends CardImpl {

    public BarbarianRiftcutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.HUMAN, SubType.BARBARIAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        // {R}, Sacrifice Barbarian Riftcutter: Destroy target land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ColoredManaCost(ColoredManaSymbol.R));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private BarbarianRiftcutter(final BarbarianRiftcutter card) {
        super(card);
    }

    @Override
    public BarbarianRiftcutter copy() {
        return new BarbarianRiftcutter(this);
    }
}
