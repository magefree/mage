
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;

/**
 *
 * @author LevelX2
 */
public final class TombOfTheSpiritDragon extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("colorless creature you control");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public TombOfTheSpiritDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // T: Add 1
        this.addAbility(new ColorlessManaAbility());
        // 2, T: You gain 1 life for each colorless creature you control
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(new PermanentsOnBattlefieldCount(filter))
                .setText("you gain 1 life for each colorless creature you control"), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TombOfTheSpiritDragon(final TombOfTheSpiritDragon card) {
        super(card);
    }

    @Override
    public TombOfTheSpiritDragon copy() {
        return new TombOfTheSpiritDragon(this);
    }
}
