

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class NumaiOutcast extends CardImpl {

    public NumaiOutcast (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new BushidoAbility(2));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ColoredManaCost(ColoredManaSymbol.B));
        ability.addCost(new PayLifeCost(5));
        this.addAbility(ability);
    }

    private NumaiOutcast(final NumaiOutcast card) {
        super(card);
    }

    @Override
    public NumaiOutcast copy() {
        return new NumaiOutcast(this);
    }

}
