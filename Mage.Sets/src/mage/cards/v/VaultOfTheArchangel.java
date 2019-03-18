
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class VaultOfTheArchangel extends CardImpl {

    public VaultOfTheArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {2}{W}{B}, {tap}: Creatures you control gain deathtouch and lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent("Creatures"), false),
                new ManaCostsImpl("{2}{W}{B}"));
        ability.addEffect(new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent("Creatures"), false));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public VaultOfTheArchangel(final VaultOfTheArchangel card) {
        super(card);
    }

    @Override
    public VaultOfTheArchangel copy() {
        return new VaultOfTheArchangel(this);
    }
}
