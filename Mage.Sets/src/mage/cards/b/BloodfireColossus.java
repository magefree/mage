

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author Loki
 */
public final class BloodfireColossus extends CardImpl {

    public BloodfireColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageEverythingEffect(6, "it"), new ColoredManaCost(ColoredManaSymbol.R));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BloodfireColossus(final BloodfireColossus card) {
        super(card);
    }

    @Override
    public BloodfireColossus copy() {
        return new BloodfireColossus(this);
    }

}
