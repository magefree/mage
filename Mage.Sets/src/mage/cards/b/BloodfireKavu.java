

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author Loki
 */
public final class BloodfireKavu extends CardImpl {

    public BloodfireKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.KAVU);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageAllEffect(2, new FilterCreaturePermanent()), new ColoredManaCost(ColoredManaSymbol.R));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BloodfireKavu(final BloodfireKavu card) {
        super(card);
    }

    @Override
    public BloodfireKavu copy() {
        return new BloodfireKavu(this);
    }

}
